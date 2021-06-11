package backend;

/**
 * Provides an interface for all Backend operations.
 */
public interface Api {

    String registerPlayer(String name);

    void createNewGame(String userId);

    void joinGame(String userId, String gameId);

}
