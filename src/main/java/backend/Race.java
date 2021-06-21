package backend;

import java.util.*;

import protocol.*;
import server.PushService;

/**
 * Represents a single race.
 */
class Race {

  private final String textToType;
  private final Map<String, Player> players;
  private final PushService pushService;
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
    player.updateProgress(snapshot);
    // TODO: Only for testing, in the future send updates in interval
    broadcastUpdate();
  }

  void broadcastUpdate() {
    List<PlayerUpdate> updates = new ArrayList<>();
    for(Map.Entry<String, Player> entry : players.entrySet()) {
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
