package app.model;

import app.ApplicationState;
import client.Client;
import client.ClientObserver;
import java.util.List;

import javafx.application.Platform;
import protocol.LobbyModel;

public class StartScreenModel implements ClientObserver {

  private StartScreenModelObserver observer;

  public void register(String name) {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribe(this);
    client.registerUser(name);
  }

  public void setObserver(StartScreenModelObserver observer) {
    this.observer = observer;
  }

  @Override
  public void registered(String userId) {
    ApplicationState.getInstance().setUserId(userId);
    if (observer != null) {
      Platform.runLater(() -> observer.registered());
    }
    ApplicationState.getInstance().getClient().unsubscribe(this);
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    // TODO: remove unused functions from interface
  }
}
