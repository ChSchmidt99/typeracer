package app.model;

import app.ApplicationState;
import client.Client;
import client.ClientObserver;
import java.util.List;

import client.ErrorObserver;
import javafx.application.Platform;
import protocol.LobbyData;

/** Model for StartScreen View. */
public class StartScreenModel implements ClientObserver, ErrorObserver {

  private StartScreenModelObserver observer;

  /**
   * Call to register a new user.
   *
   * @param name of the user
   */
  public void register(String name) {
    Client client = ApplicationState.getInstance().getClient();
    subscribe();
    client.registerUser(name);
  }

  /**
   * Set Observer.
   *
   * @param observer ovserver
   */
  public void setObserver(StartScreenModelObserver observer) {
    this.observer = observer;
  }

  @Override
  public void registered(String userId) {
    unsubscribe();
    ApplicationState.getInstance().setUserId(userId);
    if (observer != null) {
      Platform.runLater(() -> observer.registered());
    }
  }

  @Override
  public void receivedOpenLobbies(List<LobbyData> lobbies) {
    // TODO: remove unused functions from interface
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
    client.subscribe(this);
  }

  private void unsubscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.unsubscribeErrors(this);
    client.unsubscribe(this);
  }
}
