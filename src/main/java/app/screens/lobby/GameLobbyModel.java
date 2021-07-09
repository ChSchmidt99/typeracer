package app.screens.lobby;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import java.util.List;
import javafx.application.Platform;
import protocol.ChatMessageData;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for GameLobby View. */
public class GameLobbyModel implements LobbyObserver, ErrorObserver {

  private LobbyData lobby;

  private GameLobbyModelObserver observer;

  public GameLobbyModel(LobbyData lobby) {
    this.lobby = lobby;
    subscribe();
  }

  @Override
  public void gameStarting(RaceData race) {
    if (observer != null) {
      Platform.runLater(() -> observer.startedRace(race));
    }
    unsubscribe();
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    this.lobby = lobby;
    if (observer != null) {
      Platform.runLater(() -> observer.updatedLobby());
    }
  }

  @Override
  public void receivedChatHistory(List<ChatMessageData> chatHistory) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedChatHistory(chatHistory));
    }
  }

  @Override
  public void receivedError(String message) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedError(message));
    }
  }

  void setObserver(GameLobbyModelObserver observer) {
    this.observer = observer;
  }

  LobbyData getLobby() {
    return lobby;
  }

  void leaveLobby() {
    Client client = ApplicationState.getInstance().getClient();
    client.leaveLobby();
    unsubscribe();
  }

  void setReady(boolean isReady) {
    Client client = ApplicationState.getInstance().getClient();
    client.setIsReady(isReady);
  }

  void startRace() {
    Client client = ApplicationState.getInstance().getClient();
    client.startRace();
  }

  void requestHistory() {
    Client client = ApplicationState.getInstance().getClient();
    client.requestChatHistory();
  }

  void sendMessage(String message) {
    Client client = ApplicationState.getInstance().getClient();
    client.sendChatMessage(message);
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
