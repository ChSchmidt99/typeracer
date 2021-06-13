package backend;

/**
 * Provides an interface for all Backend operations.
 */
public interface Api {

  void registerPlayer(String connectionId, String name);

  void createNewGame(String connectionId, String userId);

  void joinGame(String connectionId, String userId, String gameId);

}
