package app.model;

import protocol.LobbyData;

/** Interface to receive CreateModel updates. */
public interface CreateModelObserver {

  void joinedLobby(LobbyData lobby);
}
