package backend;

import database.Database;
import database.MockDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyModel;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;

/**
 * Represents one game currently managed by the server.
 */
class Lobby {

  private final String lobbyId;
  private final HashMap<String, Player> players;
  private final PushService pushService;
  private final Database database;
  private Race race;

  Lobby(String lobbyId, PushService pushService) {
    this.players = new HashMap<>();
    this.lobbyId = lobbyId;
    this.pushService = pushService;
    // TODO: replace with real Database once ready
    this.database = new MockDatabase();
  }

  void join(String connectionId, String userId) {
    Player player = new Player(userId, this.database.getUsername(userId));
    players.put(connectionId, player);
    broadcastLobbyUpdate();
  }

  void leave(String connectionId) {
    // TODO: Assign new host, if host leaves
    players.remove(connectionId);
    broadcastLobbyUpdate();
  }

  void startGame() {
    this.race = new Race(this.database, getReadyPlayers());
    broadcast(ResponseFactory.makeRaceStartingResponse(this.race.getModel()));
  }

  LobbyModel lobbyModel() {
    List<String> playerNames = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      playerNames.add(entry.getValue().getName());
    }
    return new LobbyModel(lobbyId, playerNames, isRunning());
  }

  void setPlayerReady(String connectionId, boolean isReady) {
    players.get(connectionId).setIsReady(isReady);
  }

  private List<String> getReadyPlayers() {
    List<String> readyPlayers = new ArrayList<>();
    for (Map.Entry<String, Player> entry : players.entrySet()) {
      if (entry.getValue().getIsReady()) {
        readyPlayers.add(entry.getValue().getName());
      }
    }
    return readyPlayers;
  }

  private boolean isRunning() {
    if (this.race == null) {
      return false;
    }
    return this.race.getIsRunning();
  }

  private void broadcast(Response response) {
    pushService.sendResponse(players.keySet(), response);
  }

  private void broadcastLobbyUpdate() {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    broadcast(response);
  }

}
