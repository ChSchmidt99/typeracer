package app.screens.browser;

import java.util.List;
import protocol.LobbyData;

/** Observer for OpenLobbies View. */
interface LobbyBrowserObserver {

  void receivedOpenLobbies(List<LobbyData> lobbies);

  void joinedLobby(LobbyData lobby);

  void receivedError(String message);
}
