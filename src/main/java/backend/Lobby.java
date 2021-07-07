package backend;

import database.Database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyData;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;
import util.Logger;

/** Represents one game currently managed by the server. */
class Lobby implements RaceFinishedListener {

  private final String lobbyId;
  private final HashMap<String, LobbyMember> members;
  private final PushService pushService;
  private final Database database;
  private final String name;
  private final int maxPlayers = 4;
  private Race race;

  Lobby(String lobbyId, String name, Database database, PushService pushService) {
    this.members = new HashMap<>();
    this.lobbyId = lobbyId;
    this.pushService = pushService;
    this.database = database;
    this.name = name;
  }

  @Override
  public void raceFinished() {
    broadcastLobbyUpdate();
  }

  void join(String connectionId, String userId, String iconId) {
    try {
      if (members.size() < maxPlayers) {
        String username = this.database.getUsername(userId);
        LobbyMember lobbyMember = new LobbyMember(userId, connectionId, username, iconId);
        members.put(connectionId, lobbyMember);
        broadcastLobbyUpdate();
      } else {
        Response error = ResponseFactory.makeErrorResponse("Max number of players.");
        pushService.sendResponse(connectionId, error);
      }
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
    this.race = new Race(settings, this.database.getTextToType(), readyPlayers, pushService, this);
    broadcastLobbyUpdate();
  }

  LobbyData lobbyModel() {
    List<String> playerNames = new ArrayList<>();
    for (Map.Entry<String, LobbyMember> entry : members.entrySet()) {
      playerNames.add(entry.getValue().getName());
    }
    return new LobbyData(lobbyId, playerNames, name, isRunning());
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

  void sendUpdate(String connectionId) {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    pushService.sendResponse(connectionId, response);
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
