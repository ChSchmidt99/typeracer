package backend;

import database.Database;
import database.MockDatabase;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyModel;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;
import util.Logger;

/**
 * Represents one game currently managed by the server.
 */
class Lobby {

  private final String lobbyId;
  private final HashMap<String, LobbyMember> members;
  private final PushService pushService;
  private final Database database;
  private Race race;

  Lobby(String lobbyId, PushService pushService) {
    this.members = new HashMap<>();
    this.lobbyId = lobbyId;
    this.pushService = pushService;
    // TODO: replace with real Database once ready
    this.database = new MockDatabase();
  }

  void join(String connectionId, String userId) {
    try {
      String username = this.database.getUsername(userId);
      LobbyMember lobbyMember = new LobbyMember(userId, connectionId, username);
      members.put(connectionId, lobbyMember);
      broadcastLobbyUpdate();
    } catch (IOException e) {
      Logger.logError(e.getMessage());
    }
  }

  void leave(String connectionId) {
    // TODO: Assign new host, if host leaves
    if (!members.containsKey(connectionId)) {
      Logger.logError("Called leave on non existing member");
      return;
    }
    LobbyMember member = members.get(connectionId);
    if (member.isInRace()) {
      this.race.removePlayer(connectionId);
    }
    members.remove(connectionId);
    broadcastLobbyUpdate();
  }

  void startRace(String connectionId, RaceSettings settings) {
    Map<String, Player> readyPlayers = getReadyPlayers();
    if (readyPlayers.size() == 0) {
      // TODO: Central place for all error messages
      Response error = ResponseFactory.makeErrorResponse("No players ready");
      pushService.sendResponse(connectionId, error);
      return;
    }
    this.race = new Race(settings, this.database.getTextToType(),
            readyPlayers, pushService);
  }

  LobbyModel lobbyModel() {
    List<String> playerNames = new ArrayList<>();
    for (Map.Entry<String, LobbyMember> entry : members.entrySet()) {
      playerNames.add(entry.getValue().getName());
    }
    return new LobbyModel(lobbyId, playerNames, isRunning());
  }

  void setPlayerReady(String connectionId, boolean isReady) {
    members.get(connectionId).setIsReady(isReady);
  }

  boolean isRunning() {
    if (this.race == null) {
      return false;
    }
    return this.race.getIsRunning();
  }

  Race getRace() {
    return this.race;
  }

  String getLobbyId() {
    return this.lobbyId;
  }

  boolean isEmpty() {
    return this.members.isEmpty();
  }

  private Map<String, Player> getReadyPlayers() {
    Map<String, Player> readyPlayers = new HashMap<>();
    for (Map.Entry<String, LobbyMember> entry : members.entrySet()) {
      if (entry.getValue().getIsReady()) {
        LobbyMember member = entry.getValue();
        readyPlayers.put(entry.getKey(), member.toPlayer());
      }
    }
    return readyPlayers;
  }

  private void broadcast(Response response) {
    pushService.sendResponse(members.keySet(), response);
  }

  private void broadcastLobbyUpdate() {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    broadcast(response);
  }

}
