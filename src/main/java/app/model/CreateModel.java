package app.model;

import app.ApplicationState;
import app.IconManager;
import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for CreateView. */
public class CreateModel implements LobbyObserver, ErrorObserver {

  private CreateModelObserver observer;

  /**
   * Set observer.
   *
   * @param observer observer
   */
  public void setObserver(CreateModelObserver observer) {
    this.observer = observer;
  }

  /**
   * Create a new Lobby with name.
   *
   * @param name of the lobby
   */
  public void createLobby(String name) {
    Client client = ApplicationState.getInstance().getClient();
    String userId = ApplicationState.getInstance().getUserId();
    String iconId = IconManager.getSelectedIcon().getId();
    subscribe();
    client.newLobby(userId, name, iconId);
  }

  @Override
  public void gameStarting(RaceData race) {
    // TODO: remove unused functions from client observer interface
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.joinedLobby(lobby));
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
