package backend;

import database.Database;
import database.MockDatabase;
import protocol.Response;
import protocol.ResponseFactory;
import server.Logger;
import server.PushService;

/**
 * Currently just an experimental Api implementation.
 */
public class ApiImpl implements Api {

  private final PushService pushService;
  private final GameStore gameStore;
  private final Database database;

  public ApiImpl(PushService pushService) {
    this.pushService = pushService;
    this.gameStore = new GameStore();
    this.database = new MockDatabase();
  }

  @Override
  public void registerPlayer(String connectionId, String name) {
    String userId = database.registerUser(name);
    Response response = ResponseFactory.makeErrorResponse("Not implemented");
    try {
      pushService.sendResponse(connectionId, response);
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  @Override
  public void createNewGame(String connectionId, String userId) {
    String gameId = gameStore.createNewGame(pushService);
    gameStore.joinGame(gameId, connectionId, userId);
  }

  @Override
  public void joinGame(String connectionId, String userId, String gameId) {
    System.out.println("Called join game with: " + gameId);
    Response response = ResponseFactory.makeErrorResponse("Not implemented");
    try {
      pushService.sendResponse(connectionId, response);
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  @Override
  public void leaveGame(String connectionId) {
    gameStore.leaveGame(connectionId);
  }

  @Override
  public void startGame(String connectionId) {
    gameStore.startGame(connectionId);
  }

}
