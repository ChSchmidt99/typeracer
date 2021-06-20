package client;

import java.util.List;
import protocol.LobbyModel;
import protocol.RaceModel;

/**
 * Observer to receive responses from {@link Client}.
 */
public interface ClientObserver {

  void registered(String userId);

  /**
   * Received error response from server.
   *
   * @param message error message
   */
  void receivedError(String message);

  void gameStarting(RaceModel race);

  void receivedLobbyUpdate(LobbyModel lobby);

  void receivedOpenLobbies(List<LobbyModel> lobbies);

}
