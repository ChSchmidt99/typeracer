package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceData;
import protocol.RaceResult;
import protocol.Response;
import protocol.ResponseFactory;
import protocol.UserData;
import protocol.UserResult;
import util.Logger;
import util.Timestamp;

/** Represents a single race. */
class Race {

  private final RaceSettings settings;
  private final String textToType;
  private final Map<String, Player> players;
  private final RaceFinishedListener listener;
  private final long raceStart;
  private ScheduledExecutorService scheduler;
  private RaceState state;

  private enum RaceState {
    RUNNING,
    CHECKERED_FLAG,
    FINISHED
  }

  /**
   * Create a new Race.
   *
   * @param textToType text that needs to be typed
   * @param players all players connected to the game
   */
  Race(
      RaceSettings settings,
      String textToType,
      Map<String, Player> players,
      RaceFinishedListener listener,
      long raceStart) {
    this.textToType = textToType;
    this.players = players;
    this.state = RaceState.RUNNING;
    this.settings = settings;
    this.listener = listener;
    this.raceStart = raceStart;
    broadcastGameStarting();
    startUpdates();
  }

  RaceData getModel() {
    List<UserData> out = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      out.add(entry.getValue().getUserData());
    }
    return new RaceData(this.textToType, out, raceStart);
  }

  boolean getIsRunning() {
    return this.state != RaceState.FINISHED;
  }

  void updateProgress(User user, ProgressSnapshot snapshot) {
    if (state == RaceState.FINISHED) {
      Logger.logError("Tried updating after race finished");
      return;
    }
    Player player = players.get(user.getId());
    player.updateProgress(snapshot, textToType.length());
    if (player.getUpdate().isFinished) {
      checkeredFlag();
    }
  }

  void removePlayer(User user) {
    this.players.remove(user.getId());
  }

  RaceResult getRaceResult() {
    List<Player> p = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      p.add(entry.getValue());
    }
    p.sort(Comparator.comparing(Player::getWpm));
    Collections.reverse(p);
    List<UserResult> classification = new ArrayList<>();
    for (int i = 0; i < p.size(); i++) {
      Player player = p.get(i);
      UserResult result =
          new UserResult(player.getUserData(), player.getWpm(), player.getMistakes(), i + 1);
      classification.add(result);
    }
    long duration = p.get(0).raceDuration();
    return new RaceResult(duration, classification);
  }

  private void checkeredFlag() {
    if (this.state == RaceState.CHECKERED_FLAG) {
      return;
    }
    this.state = RaceState.CHECKERED_FLAG;
    long raceStop = Timestamp.currentTimestamp() + settings.checkeredFlagDuration;
    broadcastCheckeredFlag(raceStop);
    ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
    s.schedule(this::finishRace, settings.checkeredFlagDuration, TimeUnit.SECONDS);
    s.shutdown();
  }

  private void finishRace() {
    if (this.state == RaceState.FINISHED) {
      Logger.logError("Tried finishing an already finished race");
      return;
    }
    this.state = RaceState.FINISHED;
    stopUpdates();
    if (listener != null) {
      listener.raceFinished();
    }
  }

  private void startUpdates() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(
        this::broadcastUpdate, 0, settings.updateInterval, TimeUnit.SECONDS);
  }

  private void stopUpdates() {
    if (scheduler != null) {
      scheduler.shutdown();
    }
  }

  private void broadcastCheckeredFlag(long raceStop) {
    Response response = ResponseFactory.makeCheckeredFlagResponse(raceStop);
    broadcast(response);
  }

  private void broadcastUpdate() {
    List<PlayerUpdate> updates = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      updates.add(entry.getValue().getUpdate());
    }
    Response response = ResponseFactory.makeRaceUpdatesResponse(updates);
    broadcast(response);
  }

  private void broadcastGameStarting() {
    broadcast(ResponseFactory.makeRaceStartingResponse(getModel()));
  }

  private void broadcast(Response response) {
    for (Map.Entry<String, Player> player : players.entrySet()) {
      player.getValue().getUser().sendResponse(response);
    }
  }
}
