package app.model;

import app.ApplicationState;
import client.Client;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyModel;
import protocol.RaceModel;

public class GameFinishedModel implements LobbyObserver {

  private GameFinishedModelObserver observer;

  public void setObserver(GameFinishedModelObserver observer) {
    this.observer = observer;
  }

  public void requestLobby() {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribeLobbyUpdates(this);
    client.requestLobbyUpdate();
  }

  @Override
  public void gameStarting(RaceModel race) {
    // TODO: Remove unused Observer functions
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedGameLobby(lobby));
    }
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
  }
}
