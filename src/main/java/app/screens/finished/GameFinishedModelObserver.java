package app.screens.finished;

import protocol.LobbyData;

/** Observer for GameFinishedModel. */
interface GameFinishedModelObserver {

  void receivedGameLobby(LobbyData lobbyData);

  void receivedError(String message);
}
