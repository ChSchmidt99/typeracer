package backend;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceModel;
import protocol.Response;
import protocol.ResponseFactory;
import server.Logger;
import server.PushService;
import util.Timestamp;

/** Represents a single race. */
class Race {

  // Durations in second
  private static final long UPDATE_INTERVAL = 1;
  private static final long CHECKERED_FLAG_DURATION = 5;
  private final String textToType;
  private final Map<String, Player> players;
  private final PushService pushService;
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
  Race(String textToType, Map<String, Player> players, PushService pushService) {
    this.textToType = textToType;
    this.players = players;
    this.pushService = pushService;
    this.state = RaceState.RUNNING;
    broadcastGameStarting();
    startUpdates();
  }

  RaceModel getModel() {
    List<String> playerNames = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      playerNames.add(entry.getValue().getName());
    }
    return new RaceModel(this.textToType, playerNames);
  }

  boolean getIsRunning() {
    return this.state != RaceState.FINISHED;
  }

  void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    if (state == RaceState.FINISHED) {
      Logger.logError("Tried updating after race finished");
      return;
    }
    Player player = players.get(connectionId);
    player.updateProgress(snapshot, textToType.length());
    if (player.getUpdate().isFinished) {
      checkeredFlag();
    }
  }

  void removePlayer(String connectionId) {
    this.players.remove(connectionId);
  }

  private void checkeredFlag() {
    if (this.state == RaceState.CHECKERED_FLAG) {
      Logger.logError("Race already in checkered flag state");
      return;
    }
    long raceStop = Timestamp.currentTimestamp() + CHECKERED_FLAG_DURATION;
    broadcastCheckeredFlag(raceStop);
    ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
    s.schedule(this::finishRace, CHECKERED_FLAG_DURATION,TimeUnit.SECONDS);
    s.shutdown();
  }

  private void finishRace() {
    if (this.state == RaceState.FINISHED) {
      Logger.logError("Tried finishing an already finished race");
      return;
    }
    this.state = RaceState.FINISHED;
    stopUpdates();
  }

  private void startUpdates() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(this::broadcastUpdate, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
  }

  private void stopUpdates() {
    if (scheduler != null) {
      scheduler.shutdown();
    }
  }

  private void broadcastCheckeredFlag(long raceStop) {
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      Response response = ResponseFactory.makeCheckeredFlagResponse(raceStop);
      pushService.sendResponse(entry.getKey(), response);
    }
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
    Set<String> connectionIds = players.keySet();
    pushService.sendResponse(connectionIds, response);
  }
}
