package app.screens.create;

import protocol.LobbyData;

/** Interface to receive CreateModel updates. */
interface CreateModelObserver {

  void joinedLobby(LobbyData lobby);

  void receivedError(String message);
}
