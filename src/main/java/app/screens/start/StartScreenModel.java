package app.screens.start;

import app.ApplicationState;
import app.IconManager;
import client.Client;
import client.ClientObserver;
import client.ErrorObserver;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import protocol.LobbyData;

/** Model for StartScreen View. */
public class StartScreenModel implements ClientObserver, ErrorObserver {

  private StartScreenModelObserver observer;

  void register(String name) throws IOException {
    if (ApplicationState.getInstance().getClient() == null) {
      ApplicationState.getInstance().newClient();
    }
    Client client = ApplicationState.getInstance().getClient();
    subscribe();
    client.registerUser(name, IconManager.getSelectedIcon().getId());
  }

  void registerSingleplayer(String name) throws IOException {}

  void setObserver(StartScreenModelObserver observer) {
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
  public void receivedOpenLobbies(List<LobbyData> lobbies) {}

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
