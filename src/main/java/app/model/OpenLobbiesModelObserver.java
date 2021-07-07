package app.model;

import java.util.List;
import protocol.LobbyModel;

public interface OpenLobbiesModelObserver {

  void receivedOpenLobbies(List<LobbyModel> lobbies);

  void joinedLobby(LobbyModel lobby);
}
