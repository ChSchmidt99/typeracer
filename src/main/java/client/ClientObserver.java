package client;

import java.util.List;
import protocol.LobbyModel;

/** Observer to receive responses from {@link Client}. */
public interface ClientObserver {

  /**
   * Called with userId when successfully registered.
   *
   * @param userId id from server
   */
  void registered(String userId);

  /**
   * Called when a list of all open lobbies is received.
   *
   * @param lobbies list of open lobbies
   */
  void receivedOpenLobbies(List<LobbyModel> lobbies);
}
