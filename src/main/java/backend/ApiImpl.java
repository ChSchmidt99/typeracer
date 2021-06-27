package backend;

import database.Database;
import database.MockDatabase;
import java.util.List;
import protocol.LobbyModel;
import protocol.ProgressSnapshot;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;


/**
 * Currently just an experimental Api implementation.
 */
public class ApiImpl implements Api {

  private final PushService pushService;
  private final SessionStore sessionStore;
  private final Database database;
  private final RaceSettings raceSettings;

  /**
   * Create an instance implementing the Api interface.
   *
   * @param pushService service where responses are sent to
   */
  public ApiImpl(PushService pushService) {
    this.pushService = pushService;
    this.sessionStore = new SessionStore();
    this.database = new MockDatabase();
    this.raceSettings = new RaceSettings(5, 1);
  }

  @Override
  public void registerPlayer(String connectionId, String name) {
    String userId = database.registerUser(name);
    Response response = ResponseFactory.makeRegisteredResponse(userId);
    pushService.sendResponse(connectionId, response);
  }

  @Override
  public void createNewLobby(String connectionId, String userId) {
    String lobbyId = sessionStore.createNewLobby(connectionId, pushService);
    sessionStore.joinLobby(lobbyId, connectionId, userId);
  }

  @Override
  public void joinLobby(String lobbyId, String connectionId, String userId) {
    sessionStore.joinLobby(lobbyId, connectionId, userId);
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
  public void setPlayerReady(String connectionId, boolean isReady) {
    sessionStore.setPlayerReady(connectionId, isReady);
  }

  @Override
  public void updateProgress(String connectionId, ProgressSnapshot snapshot) {
    sessionStore.updateProgress(connectionId, snapshot);
  }

}
