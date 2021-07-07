package app.model;

import protocol.LobbyModel;

public interface CreateModelObserver {

  void joinedLobby(LobbyModel lobby);
}
