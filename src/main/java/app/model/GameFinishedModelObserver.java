package app.model;

import protocol.LobbyModel;

public interface GameFinishedModelObserver {

  void receivedGameLobby(LobbyModel lobbyModel);
}
