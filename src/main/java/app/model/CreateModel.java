package app.model;

import app.ApplicationState;
import app.IconManager;
import client.Client;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyModel;
import protocol.RaceModel;

public class CreateModel implements LobbyObserver {

  private CreateModelObserver observer;

  public void setObserver(CreateModelObserver observer) {
    this.observer = observer;
  }

  public void createLobby(String name) {
    Client client = ApplicationState.getInstance().getClient();
    String userId = ApplicationState.getInstance().getUserId();
    String iconId = IconManager.getSelectedIcon().getId();
    client.subscribeLobbyUpdates(this);
    client.newLobby(userId, name, iconId);
  }

  @Override
  public void gameStarting(RaceModel race) {
    // TODO: remove unused functions from client observer interface
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.joinedLobby(lobby));
    }
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
  }
}
