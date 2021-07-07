package app.model;

import app.ApplicationState;
import app.IconManager;
import client.Client;
import client.ClientObserver;
import client.LobbyObserver;
import java.util.List;
import javafx.application.Platform;
import protocol.LobbyModel;
import protocol.RaceModel;

public class OpenLobbiesModel implements LobbyObserver, ClientObserver {

  private OpenLobbiesModelObserver observer;

  public void setObserver(OpenLobbiesModelObserver observer) {
    this.observer = observer;
  }

  public void requestLobbyList() {
    Client client = ApplicationState.getInstance().getClient();
    client.requestLobbies();
  }

  public void joinLobby(String lobbyId) {
    Client client = ApplicationState.getInstance().getClient();
    String userId = ApplicationState.getInstance().getUserId();
    String iconId = IconManager.getSelectedIcon().getId();
    client.joinLobby(userId, lobbyId, iconId);
  }

  @Override
  public void gameStarting(RaceModel race) {
    // TODO: remove unused functions from interface
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
    Platform.runLater(() -> observer.joinedLobby(lobby));
  }

  @Override
  public void registered(String userId) {
    // TODO: remove unused functions from interface
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    Platform.runLater(() -> observer.receivedOpenLobbies(lobbies));
  }
}
