package app.screens.create;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import java.util.List;
import javafx.application.Platform;
import protocol.ChatMessageData;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for CreateView. */
public class CreateModel implements LobbyObserver, ErrorObserver {

  private CreateModelObserver observer;

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.joinedLobby(lobby));
    }
    unsubscribe();
  }

  @Override
  public void receivedChatHistory(List<ChatMessageData> chatHistory) {}

  @Override
  public void receivedError(String message) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedError(message));
    }
  }

  @Override
  public void gameStarting(RaceData race) {}

  void setObserver(CreateModelObserver observer) {
    this.observer = observer;
  }

  void createLobby(String name) {
    Client client = ApplicationState.getInstance().getClient();
    subscribe();
    client.newLobby(name);
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
