package backend;

import database.Database;
import java.util.List;
import protocol.LobbyData;
import protocol.ProgressSnapshot;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;
import util.Logger;

/** Currently just an experimental Api implementation. */
public class ApiImpl implements Api {

  private final PushService pushService;
  private final LobbyStore lobbyStore;
  private final UserStore userStore;
  private final Database database;
  private final RaceSettings raceSettings;

  /**
   * Create an instance implementing the Api interface.
   *
   * @param pushService service where responses are sent to
   * @param database database
   */
  public ApiImpl(PushService pushService, Database database) {
    this.pushService = pushService;
    this.database = database;
    this.userStore = new UserStore();
    this.lobbyStore = new LobbyStore(this.database);
    this.raceSettings = new RaceSettings(5, 1);
  }

  @Override
  public void registerPlayer(String connectionId, String name, String iconId) {
    try {
      String userId = database.registerUser(name);
      this.userStore.addNewUser(
          connectionId, new User(userId, name, iconId, connectionId, pushService));
      Response response = ResponseFactory.makeRegisteredResponse(userId);
      pushService.sendResponse(connectionId, response);
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  @Override
  public void createNewLobby(String connectionId, String lobbyName) {
    User user = userStore.getUser(connectionId);
    String lobbyId = lobbyStore.createNewLobby(user, lobbyName);
    try {
      lobbyStore.addToLobby(lobbyId, user);
    } catch (Exception e) {
      Response error = ResponseFactory.makeErrorResponse(e.getMessage());
      pushService.sendResponse(connectionId, error);
    }
  }

  @Override
  public void joinLobby(String connectionId, String lobbyId) {
    try {
      User user = userStore.getUser(connectionId);
      lobbyStore.addToLobby(lobbyId, user);
    } catch (Exception e) {
      Response error = ResponseFactory.makeErrorResponse(e.getMessage());
      pushService.sendResponse(connectionId, error);
    }
  }

  @Override
  public void leaveLobby(String connectionId) {
    User user = userStore.getUser(connectionId);
    lobbyStore.removeFromLobby(user);
  }

  @Override
  public void startRace(String connectionId) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null) {
      lobby.startRace(user, raceSettings);
    }
  }

  @Override
  public void getLobbies(String connectionId) {
    List<LobbyData> lobbies = lobbyStore.getOpenLobbies();
    Response response = ResponseFactory.makeLobbiesResponse(lobbies);
    pushService.sendResponse(connectionId, response);
  }

  @Override
  public void sendLobbyUpdate(String connectionId) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null) {
      lobby.sendUpdate(user);
    }
  }

  @Override
  public void setPlayerReady(String connectionId, boolean isReady) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null) {
      lobby.setPlayerReady(user, isReady);
    }
  }

  @Override
  public void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null && lobby.isRunning()) {
      Race race = lobby.getRace();
      race.updateProgress(user, snapshot);
    }
  }

  @Override
  public void sendPreviousRaceResult(String connectionId) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null) {
      lobby.sendPreviousRaceResult(user);
    }
  }

  @Override
  public void sendChat(String connectionId, String message) {
    User user = userStore.getUser(connectionId);
    Lobby lobby = lobbyStore.getLobby(user);
    if (lobby != null) {
      ChatMessage chatMessage = new ChatMessage(user, message);
      lobby.sendChatMessage(chatMessage);
    }
  }
}
