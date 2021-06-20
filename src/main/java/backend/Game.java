package backend;

import database.Database;
import database.MockDatabase;
import protocol.Response;
import protocol.ResponseFactory;
import server.PushService;

import java.util.HashMap;

/**
 * Represents one game currently managed by the server.
 */
class Game {

  private final String gameId;
  private boolean isRunning;
  private final HashMap<String, String> players;
  private final PushService pushService;
  private final Database database;

  Game(String gameId, PushService pushService) {
    this.players = new HashMap<>();
    this.gameId = gameId;
    this.isRunning = false;
    this.pushService = pushService;
    // TODO: replace with real Database once ready
    this.database = new MockDatabase();
  }

  void join(String connectionId, String userId) {
    String username = database.getUsername(userId);
    broadcast(ResponseFactory.makePlayerJoinedResponse(username));
    players.put(connectionId, userId);
    Response response = ResponseFactory.makeJoinedGameResponse(gameId, isRunning);
    pushService.sendResponse(connectionId, response);
  }

  void leave(String connectionId) {
    String userId = players.get(connectionId);
    players.remove(connectionId);
    broadcast(ResponseFactory.makePlayerLeftResponse(userId));
  }

  void startGame() {
    this.isRunning = true;
    String text = database.getTextToType();
    broadcast(ResponseFactory.makeGameStartingResponse(text));
  }

  private void broadcast(Response response) {
    pushService.sendResponse(players.keySet(), response);
  }

}
