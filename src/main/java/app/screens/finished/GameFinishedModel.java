package app.screens.finished;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import java.util.List;
import javafx.application.Platform;
import protocol.ChatMessageData;
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

  @Override
  public void gameStarting(RaceData race) {}

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedGameLobby(lobby));
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

  void setObserver(GameFinishedModelObserver observer) {
    this.observer = observer;
  }

  RaceResult getRaceResult() {
    return this.result;
  }

  void requestLobby() {
    Client client = ApplicationState.getInstance().getClient();
    subscribe();
    client.requestLobbyUpdate();
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
