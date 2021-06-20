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
  private boolean isRunning;
  private final String hostConnectionId;
  private final HashMap<String, String> players;
  private final PushService pushService;
  private final Database database;

  Lobby(String lobbyId, String hostConnectionId, PushService pushService) {
    this.hostConnectionId = hostConnectionId;
    this.players = new HashMap<>();
    this.lobbyId = lobbyId;
    this.isRunning = false;
    this.pushService = pushService;
    // TODO: replace with real Database once ready
    this.database = new MockDatabase();
  }

  void join(String connectionId, String userId) {
    players.put(connectionId, userId);
    broadcastLobbyUpdate();
  }

  void leave(String connectionId) {
    // TODO: Assign new host, if host leaves
    players.remove(connectionId);
    broadcastLobbyUpdate();
  }

  void startGame() {
    this.isRunning = true;
    String text = database.getTextToType();
    broadcast(ResponseFactory.makeRaceStartingResponse(text));
  }

  LobbyModel lobbyModel() {
    List<String> playerNames = new ArrayList<>();
    for (Map.Entry<String, String> entry : players.entrySet()) {
      playerNames.add(database.getUsername(players.get(entry.getValue())));
    }
    return new LobbyModel(lobbyId, playerNames, isRunning);
  }

  private void broadcast(Response response) {
    pushService.sendResponse(players.keySet(), response);
  }

  private void broadcastLobbyUpdate() {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    broadcast(response);
  }

}
