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

}
