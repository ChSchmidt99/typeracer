package client;

import java.io.Closeable;
import protocol.ProgressSnapshot;

/** Provides interface for all Server requests. */
public interface Client extends ObserverHandler, Closeable {

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
   * @param lobbyName name of lobby
   */
  void newLobby(String userId, String lobbyName, String iconId);

  /**
   * Join an ongoing lobby.
   *
   * @param userId of player
   * @param gameId of game
   */
  void joinLobby(String userId, String gameId, String iconId);

  /** Start a race in the current lobby. */
  void startRace();

  /** Leave the current lobby. */
  void leaveLobby();

  /** Request a list of all open lobbies. */
  void requestLobbies();

  void requestLobbyUpdate();

  void requestPreviousRaceResult();

  /**
   * Set ready or not. If ready, client will join the next race.
   *
   * @param isReady whether or not the user is ready
   */
  void setIsReady(boolean isReady);

  void sendProgressUpdate(ProgressSnapshot snapshot);
}
