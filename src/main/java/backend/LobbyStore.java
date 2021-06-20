package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyModel;
import server.PushService;

/**
 * Holds all currently running games and forwards calls to the correct instances.
 */
class LobbyStore {

  private final IdGenerator generator;
  private final HashMap<String, Lobby> lobbies;
  private final HashMap<String, String> connectionIds;

  LobbyStore() {
    this.generator = new IdGenerator(0);
    this.lobbies = new HashMap<>();
    this.connectionIds = new HashMap<>();
  }

  String createNewLobby(String connectionId, PushService pushService) {
    String lobbyId = generator.getId();
    Lobby lobby = new Lobby(lobbyId, connectionId, pushService);
    lobbies.put(lobbyId, lobby);
    return lobbyId;
  }

  void joinLobby(String lobbyId, String connectionId, String userId) {
    connectionIds.put(connectionId, lobbyId);
    Lobby lobby = lobbies.get(lobbyId);
    lobby.join(connectionId, userId);
  }

  void leaveLobby(String connectionId) {
    Lobby lobby = getLobby(connectionId);
    lobby.leave(connectionId);
    connectionIds.remove(connectionId);
    // TODO: remove lobby when empty
  }

  void startGame(String connectionId) {
    Lobby lobby = getLobby(connectionId);
    lobby.startGame();
  }

  List<LobbyModel> getOpenLobbies() {
    List<LobbyModel> openLobbies = new ArrayList<>();
    for (Map.Entry<String, Lobby> entry : this.lobbies.entrySet()) {
      Lobby lobby = entry.getValue();
      openLobbies.add(lobby.lobbyModel());
    }
    return openLobbies;
  }

  private Lobby getLobby(String connectionId) {
    String gameId = connectionIds.get(connectionId);
    return lobbies.get(gameId);
  }

}
