package backend;

import server.PushService;

import java.util.HashMap;

/**
 * Holds all currently running games and forwards calls to the correct instances.
 */
class GameStore {

  private final IdGenerator generator;
  private final HashMap<String, Game> games;
  private final HashMap<String, String> connectionIds;

  GameStore() {
    this.generator = new IdGenerator(0);
    this.games = new HashMap<>();
    this.connectionIds = new HashMap<>();
  }

  String createNewGame(PushService pushService) {
    String gameId = generator.getId();
    Game game = new Game(gameId, pushService);
    games.put(gameId, game);
    return gameId;
  }

  void joinGame(String gameId, String connectionId, String userId) {
    connectionIds.put(connectionId, gameId);
    Game game = games.get(gameId);
    game.join(connectionId, userId);
  }

  void leaveGame(String connectionId) {
    Game game = getGame(connectionId);
    game.leave(connectionId);
    connectionIds.remove(connectionId);
  }

  void startGame(String connectionId) {
    Game game = getGame(connectionId);
    game.startGame();
  }

  private Game getGame(String connectionId) {
    String gameId = connectionIds.get(connectionId);
    return games.get(gameId);
  }

}
