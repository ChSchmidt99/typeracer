package backend;

import database.Database;
import database.MockDatabase;
import java.util.List;
import protocol.LobbyModel;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;


/**
 * Currently just an experimental Api implementation.
 */
public class ApiImpl implements Api {

  private final PushService pushService;
  private final LobbyStore lobbyStore;
  private final Database database;

  /**
   * Create an instance implementing the Api interface.
   *
   * @param pushService service where responses are sent to
   */
  public ApiImpl(PushService pushService) {
    this.pushService = pushService;
    this.lobbyStore = new LobbyStore();
    this.database = new MockDatabase();
  }

  @Override
  public void registerPlayer(String connectionId, String name) {
    String userId = database.registerUser(name);
    Response response = ResponseFactory.makeRegisteredResponse(userId);
    pushService.sendResponse(connectionId, response);
  }

  @Override
  public void createNewLobby(String connectionId, String userId) {
    String lobbyId = lobbyStore.createNewLobby(connectionId, pushService);
    lobbyStore.joinLobby(lobbyId, connectionId, userId);
  }

  @Override
  public void joinLobby(String lobbyId, String connectionId, String userId) {
    lobbyStore.joinLobby(lobbyId, connectionId, userId);
  }

  @Override
  public void leaveLobby(String connectionId) {
    lobbyStore.leaveLobby(connectionId);
  }

  @Override
  public void startRace(String connectionId) {
    lobbyStore.startGame(connectionId);
  }

  @Override
  public void getLobbies(String connectionId) {
    List<LobbyModel> lobbies = lobbyStore.getOpenLobbies();
    Response response = ResponseFactory.makeLobbiesResponse(lobbies);
    pushService.sendResponse(connectionId, response);
  }

  @Override
  public void setPlayerReady(String connectionId, boolean isReady) {
    lobbyStore.setPlayerReady(connectionId, isReady);
  }

}
