package backend;

import database.Database;
import java.util.List;
import protocol.LobbyModel;
import protocol.ProgressSnapshot;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;
import util.Logger;

/** Currently just an experimental Api implementation. */
public class ApiImpl implements Api {

  private final PushService pushService;
  private final SessionStore sessionStore;
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
    this.sessionStore = new SessionStore(this.database);
    this.raceSettings = new RaceSettings(5, 1);
  }

  @Override
  public void registerPlayer(String connectionId, String name) {
    try {
      String userId = database.registerUser(name);
      Response response = ResponseFactory.makeRegisteredResponse(userId);
      pushService.sendResponse(connectionId, response);
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  @Override
  public void createNewLobby(String connectionId, String userId, String lobbyName, String iconId) {
    String lobbyId = sessionStore.createNewLobby(connectionId, lobbyName, pushService);
    sessionStore.joinLobby(lobbyId, connectionId, userId, iconId);
  }

  @Override
  public void joinLobby(String lobbyId, String connectionId, String userId, String iconId) {
    sessionStore.joinLobby(lobbyId, connectionId, userId, iconId);
  }

  @Override
  public void leaveLobby(String connectionId) {
    sessionStore.leaveLobby(connectionId);
  }

  @Override
  public void startRace(String connectionId) {
    sessionStore.startGame(connectionId, raceSettings);
  }

  @Override
  public void getLobbies(String connectionId) {
    List<LobbyModel> lobbies = sessionStore.getOpenLobbies();
    Response response = ResponseFactory.makeLobbiesResponse(lobbies);
    pushService.sendResponse(connectionId, response);
  }

  @Override
  public void sendLobbyUpdate(String connectionId) {
    sessionStore.sendLobbyUpdate(connectionId);
  }

  @Override
  public void setPlayerReady(String connectionId, boolean isReady) {
    sessionStore.setPlayerReady(connectionId, isReady);
  }

  @Override
  public void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    sessionStore.updateProgress(connectionId, snapshot);
  }
}
