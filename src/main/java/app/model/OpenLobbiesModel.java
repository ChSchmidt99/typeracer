package app.model;

import app.ApplicationState;
import app.IconManager;
import client.Client;
import client.ClientObserver;
import client.LobbyObserver;
import java.util.List;
import javafx.application.Platform;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for OpenLobbies View. */
public class OpenLobbiesModel implements LobbyObserver, ClientObserver {

  private OpenLobbiesModelObserver observer;

  /**
   * Set Observer.
   *
   * @param observer ovserver
   */
  public void setObserver(OpenLobbiesModelObserver observer) {
    this.observer = observer;
  }

  public void requestLobbyList() {
    Client client = ApplicationState.getInstance().getClient();
    client.requestLobbies();
  }

  /**
   * Call to join lobby.
   *
   * @param lobbyId id of lobby
   */
  public void joinLobby(String lobbyId) {
    Client client = ApplicationState.getInstance().getClient();
    String userId = ApplicationState.getInstance().getUserId();
    String iconId = IconManager.getSelectedIcon().getId();
    client.joinLobby(userId, lobbyId, iconId);
  }

  @Override
  public void gameStarting(RaceData race) {
    // TODO: remove unused functions from interface
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
    Platform.runLater(() -> observer.joinedLobby(lobby));
  }

  @Override
  public void registered(String userId) {
    // TODO: remove unused functions from interface
  }

  @Override
  public void receivedOpenLobbies(List<LobbyData> lobbies) {
    Platform.runLater(() -> observer.receivedOpenLobbies(lobbies));
  }
}
