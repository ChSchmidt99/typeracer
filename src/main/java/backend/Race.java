package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

/** Represents a single race. */
class Race {

  private static final long UPDATE_INTERVAL = 1;

  private final String textToType;
  private final Map<String, Player> players;
  private final PushService pushService;
  private ScheduledExecutorService scheduler;
  private boolean isRunning;

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
    this.isRunning = true;
    broadCastGameStarting();
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
    return isRunning;
  }

  void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    Player player = players.get(connectionId);
    player.updateProgress(snapshot, textToType.length());
  }

  void finishRace() {
    stopUpdates();
    this.isRunning = false;
  }

  void removePlayer(String connectionId) {
    this.players.remove(connectionId);
  }

  private void startUpdates() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(this::broadcastUpdate, UPDATE_INTERVAL,0, TimeUnit.SECONDS);
  }

  private void stopUpdates() {
    if (scheduler != null) {
      scheduler.shutdown();
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

  private void broadCastGameStarting() {
    broadcast(ResponseFactory.makeRaceStartingResponse(getModel()));
  }

  private void broadcast(Response response) {
    Set<String> connectionIds = players.keySet();
    pushService.sendResponse(connectionIds, response);
  }
}
