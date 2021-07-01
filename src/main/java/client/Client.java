package client;

import protocol.ProgressSnapshot;

/** Provides interface for all Server communication. */
public interface Client {

  /**
   * Register a new user.
   *
   * @param userName name of user
   */
  void registerUser(String userName);

  /**
   * Start a new lobby.
   *
   * @param userId of host
   */
  void newLobby(String userId, String lobbyName);

  /**
   * Join an ongoing lobby.
   *
   * @param userId of player
   * @param gameId of game
   */
  void joinLobby(String userId, String gameId);

  /** Start a race in the current lobby. */
  void startRace();

  /** Leave the current lobby. */
  void leaveLobby();

  /** Request a list of all open lobbies. */
  void requestLobbies();

  /**
   * Set ready or not. If ready, client will join the next race.
   *
   * @param isReady whether or not the user is ready
   */
  void setIsReady(boolean isReady);

  void sendProgressUpdate(ProgressSnapshot snapshot);

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
