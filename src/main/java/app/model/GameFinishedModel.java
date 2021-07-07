package app.model;

import app.ApplicationState;
import client.Client;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for GameFinished View. */
public class GameFinishedModel implements LobbyObserver {

  private GameFinishedModelObserver observer;

  /**
   * Set Observer.
   *
   * @param observer ovserver
   */
  public void setObserver(GameFinishedModelObserver observer) {
    this.observer = observer;
  }

  /** Request a lobby updated. */
  public void requestLobby() {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribeLobbyUpdates(this);
    client.requestLobbyUpdate();
  }

  @Override
  public void gameStarting(RaceData race) {
    // TODO: Remove unused Observer functions
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedGameLobby(lobby));
    }
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
  }
}
