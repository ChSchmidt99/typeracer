package client;

import protocol.LobbyModel;
import protocol.RaceModel;

/** Implement to receive updates about the current Lobby. */
public interface LobbyObserver {

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
}
