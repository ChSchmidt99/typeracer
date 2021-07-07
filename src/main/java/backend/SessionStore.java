package backend;

import database.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import protocol.LobbyData;
import protocol.ProgressSnapshot;
import server.PushService;

/** Holds all currently running lobbies and forwards calls to the correct instances. */
class SessionStore {

  private final IdGenerator generator;
  private final HashMap<String, Lobby> lobbies;
  private final HashMap<String, String> connectionIds;
  private final Database database;

  SessionStore(Database database) {
    this.generator = new IdGenerator(0);
    this.lobbies = new HashMap<>();
    this.connectionIds = new HashMap<>();
    this.database = database;
  }

  String createNewLobby(String connectionId, String name, PushService pushService) {
    String lobbyId = generator.getId();
    Lobby lobby = new Lobby(lobbyId, name, database, pushService);
    lobbies.put(lobbyId, lobby);
    return lobbyId;
  }

  void joinLobby(String lobbyId, String connectionId, String userId, String iconId) {
    connectionIds.put(connectionId, lobbyId);
    Lobby lobby = lobbies.get(lobbyId);
    lobby.join(connectionId, userId, iconId);
  }

  void leaveLobby(String connectionId) {
    Lobby lobby = getLobby(connectionId);
    lobby.leave(connectionId);
    connectionIds.remove(connectionId);
    if (lobby.isEmpty()) {
      lobbies.remove(lobby.getLobbyId());
    }
  }

  void startGame(String connectionId, RaceSettings raceSettings) {
    Lobby lobby = getLobby(connectionId);
    lobby.startRace(connectionId, raceSettings);
  }

  List<LobbyData> getOpenLobbies() {
    List<LobbyData> openLobbies = new ArrayList<>();
    for (Map.Entry<String, Lobby> entry : this.lobbies.entrySet()) {
      Lobby lobby = entry.getValue();
      openLobbies.add(lobby.lobbyModel());
    }
    return openLobbies;
  }

  void setPlayerReady(String connectionId, boolean isReady) {
    Lobby lobby = getLobby(connectionId);
    lobby.setPlayerReady(connectionId, isReady);
  }

  void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    Lobby lobby = getLobby(connectionId);
    // TODO: Send or log error when no race running
    if (lobby.isRunning()) {
      Race race = lobby.getRace();
      race.updateProgress(connectionId, snapshot);
    }
  }

  void sendLobbyUpdate(String connectionId) {
    Lobby lobby = getLobby(connectionId);
    lobby.sendUpdate(connectionId);
  }

  void sendPreviousRaceResult(String connectionId) {
    Lobby lobby = getLobby(connectionId);
    lobby.sendPreviousRaceResult(connectionId);
  }

  Lobby getLobby(String connectionId) {
    String lobbyId = connectionIds.get(connectionId);
    return lobbies.get(lobbyId);
  }
}
