package app.model;

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

  /**
   * Set Observer.
   *
   * @param observer observer
   */
  public void setObserver(GameLobbyModelObserver observer) {
    this.observer = observer;
  }

  /**
   * Get most recent Lobby update.
   *
   * @return {@link LobbyData}
   */
  public LobbyData getLobby() {
    return lobby;
  }

  /** Call to leave lobby. */
  public void leaveLobby() {
    Client client = ApplicationState.getInstance().getClient();
    client.leaveLobby();
    unsubscribe();
  }

  /**
   * Notify server if player is ready or not.
   *
   * @param isReady whether or not player is ready
   */
  public void setReady(boolean isReady) {
    Client client = ApplicationState.getInstance().getClient();
    client.setIsReady(isReady);
  }

  /** Call to start the race. */
  public void startRace() {
    Client client = ApplicationState.getInstance().getClient();
    client.startRace();
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
    for (ChatMessageData message : chatHistory) {
      System.out.println(message.user.name + ": " + message.message);
    }
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
