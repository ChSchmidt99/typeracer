package client;

/** Holds all observer subscribe functions. */
public interface ObserverHandler {
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

  /**
   * Subscribe to receive responses.
   *
   * @param observer {@link ClientObserver}
   */
  void subscribeRaceUpdates(RaceObserver observer);

  /**
   * Unsubscribe from responses.
   *
   * @param observer {@link ClientObserver}
   */
  void unsubscribeRaceUpdates(RaceObserver observer);

  /**
   * Subscribe to receive responses.
   *
   * @param observer {@link ClientObserver}
   */
  void subscribeLobbyUpdates(LobbyObserver observer);

  /**
   * Unsubscribe from responses.
   *
   * @param observer {@link ClientObserver}
   */
  void unsubscribeLobbyUpdates(LobbyObserver observer);

  /**
   * Subscribe to receive responses.
   *
   * @param observer {@link ClientObserver}
   */
  void subscribeErrors(ErrorObserver observer);

  /**
   * Unsubscribe from responses.
   *
   * @param observer {@link ClientObserver}
   */
  void unsubscribeErrors(ErrorObserver observer);
}
