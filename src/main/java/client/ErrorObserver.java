package client;

/** Implement to receive error responses from server. */
public interface ErrorObserver {
  /**
   * Received error response from server.
   *
   * @param message error message
   */
  void receivedError(String message);
}
