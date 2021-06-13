package client;

/**
 * Provides interface for all Server communication.
 */
public interface Client {

  /**
   * Register a new user.
   *
   * @param userName name of user
   */
  void registerUser(String userName);

  /**
   * Start a new game.
   *
   * @param userId of host
   */
  void newGame(String userId);

  /**
   * Join an ongoing game.
   *
   * @param userId of player
   * @param gameId of game
   */
  void joinGame(String userId, String gameId);

  /**
   * Subscribe to receive responses.
   *
   * @param observer {@link ClientObserver}
   */
  void subscribe(ClientObserver observer);

  /**
   * Unsubscribe from responses.
   *
   * @param observer {@link ClientObserver}
   */
  void unsubscribe(ClientObserver observer);

}
