package app.model;

import java.util.List;
import protocol.LobbyData;

/** Observer for OpenLobbies View. */
public interface OpenLobbiesModelObserver {

  void receivedOpenLobbies(List<LobbyData> lobbies);

  void joinedLobby(LobbyData lobby);

  void receivedError(String message);
}
