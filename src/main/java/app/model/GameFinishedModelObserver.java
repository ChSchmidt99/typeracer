package app.model;

import protocol.LobbyData;

/** Observer for GameFinishedModel. */
public interface GameFinishedModelObserver {

  void receivedGameLobby(LobbyData lobbyData);

  void receivedError(String message);
}
