package client;

import java.util.List;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.RaceModel;

/**
 * Observer to receive responses from {@link Client}.
 */
public interface ClientObserver {

  /**
   * Called with userId when successfully registered.
   *
   * @param userId id from server
   */
  void registered(String userId);

  /**
   * Received error response from server.
   *
   * @param message error message
   */
  void receivedError(String message);

  /**
   * Called when a game in the current lobby is starting.
   *
   * @param race information about the starting race
   */
  void gameStarting(RaceModel race);

  /**
   * Called every time the lobby is updated.
   *
   * @param lobby information about the updated lobby
   */
  void receivedLobbyUpdate(LobbyModel lobby);

  /**
   * Called when a list of all open lobbies is received.
   *
   * @param lobbies list of open lobbies
   */
  void receivedOpenLobbies(List<LobbyModel> lobbies);

  /**
   * Called when the progress of players was updated.
   *
   * @param updates updated players
   */
  void receivedRaceUpdate(List<PlayerUpdate> updates);

  /**
   * Called after first player crossed finish line.
   * No more updates will be accepted by server after raceStop time.
   *
   * @param raceStop unix epoch timestamp of race end in seconds
   */
  void receivedCheckeredFlag(long raceStop);

}
