package app.model;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyData;
import protocol.RaceData;
import protocol.RaceResult;

/** Model for GameFinished View. */
public class GameFinishedModel implements LobbyObserver, ErrorObserver {

  private GameFinishedModelObserver observer;

  private final RaceResult result;

  public GameFinishedModel(RaceResult result) {
    this.result = result;
    ApplicationState.getInstance().getClient().setIsReady(false);
  }

  /**
   * Set Observer.
   *
   * @param observer ovserver
   */
  public void setObserver(GameFinishedModelObserver observer) {
    this.observer = observer;
  }

  /**
   * Get result of previous race.
   *
   * @return {@link RaceResult}
   */
  public RaceResult getRaceResult() {
    return this.result;
  }

  /** Request a lobby updated. */
  public void requestLobby() {
    Client client = ApplicationState.getInstance().getClient();
    subscribe();
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
    unsubscribe();
  }

  @Override
  public void receivedError(String message) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedError(message));
    }
  }

  private void subscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribeErrors(this);
    client.subscribeLobbyUpdates(this);
  }

  private void unsubscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.unsubscribeErrors(this);
    client.unsubscribeLobbyUpdates(this);
  }
}
