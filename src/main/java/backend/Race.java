package backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import protocol.RaceModel;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;

/**
 * Represents a single race.
 */
class Race {

  private final String textToType;
  private final List<Player> players;
  private final PushService pushService;
  private boolean isRunning;

  /**
   * Create a new Race.
   *
   * @param textToType text that needs to be typed
   * @param players all players connected to the game
   */
  Race(String textToType, List<Player> players, PushService pushService) {
    this.textToType = textToType;
    this.players = players;
    this.pushService = pushService;
    this.isRunning = true;
    broadCastGameStarting();
  }

  RaceModel getModel() {
    List<String> playerNames = new ArrayList<>();
    for (Player player : players) {
      playerNames.add(player.getName());
    }
    return new RaceModel(this.textToType, playerNames);
  }

  boolean getIsRunning() {
    return isRunning;
  }

  private void broadCastGameStarting() {
    broadcast(ResponseFactory.makeRaceStartingResponse(getModel()));
  }

  private void broadcast(Response response) {
    Set<String> connectionIds = new HashSet<>();
    for (Player player : players) {
      connectionIds.add(player.getConnectionId());
    }
    pushService.sendResponse(connectionIds, response);
  }

}
