package client;

/**
 * Observer to receive responses from {@link Client}.
 */
public interface ClientObserver {

  /**
   * Received error response from server.
   *
   * @param message error message
   */
  void receivedError(String message);

  void joinedGame(String gameId, boolean isRunning);

  void gameStarting(String text);

  void playerJoined(String name);

  void playerLeft(String name);

}
