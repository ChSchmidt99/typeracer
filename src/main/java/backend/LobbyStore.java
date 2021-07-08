package backend;

import database.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyData;

/** Manages all active lobbies and join + leave access. */
class LobbyStore {

  private final IdGenerator generator;
  private final HashMap<User, String> lobbyIds;
  private final HashMap<String, Lobby> lobbies;
  private final Database database;

  LobbyStore(Database database) {
    this.generator = new IdGenerator(0);
    this.lobbies = new HashMap<>();
    this.lobbyIds = new HashMap<>();
    this.database = database;
  }

  String createNewLobby(User user, String name) {
    String lobbyId = generator.getId();
    Lobby lobby = new Lobby(lobbyId, name, database);
    lobbyIds.put(user, lobbyId);
    lobbies.put(lobbyId, lobby);
    return lobbyId;
  }

  void addToLobby(String lobbyId, User user) throws Exception {
    if (!lobbies.containsKey(lobbyId)) {
      throw new Exception("Lobby does not exist.");
    }
    Lobby lobby = lobbies.get(lobbyId);
    lobby.join(user);
    lobbyIds.put(user, lobbyId);
  }

  void removeFromLobby(User user) {
    Lobby lobby = getLobby(user);
    if (lobby != null) {
      lobby.leave(user);
      if (lobby.isEmpty()) {
        lobbies.remove(lobby.getLobbyId());
      }
    }
    lobbyIds.remove(user);
  }

  List<LobbyData> getOpenLobbies() {
    List<LobbyData> openLobbies = new ArrayList<>();
    for (Map.Entry<String, Lobby> entry : this.lobbies.entrySet()) {
      Lobby lobby = entry.getValue();
      openLobbies.add(lobby.lobbyModel());
    }
    return openLobbies;
  }

  Lobby getLobby(User user) {
    String lobbyId = lobbyIds.get(user);
    return lobbies.get(lobbyId);
  }
}
