package app.screens.browser;

import app.ApplicationState;
import client.Client;
import client.ClientObserver;
import client.ErrorObserver;
import client.LobbyObserver;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import protocol.ChatMessageData;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for OpenLobbies View. */
public class LobbyBrowserModel implements LobbyObserver, ClientObserver, ErrorObserver, Closeable {

  /** Update interval in seconds. */
  private static final long UPDATE_INTERVAL = 1;

  private LobbyBrowserObserver observer;
  private final ScheduledExecutorService scheduler;

  /** Model for OpenLobbies view. */
  public LobbyBrowserModel() {
    scheduler = Executors.newScheduledThreadPool(1);
    subscribe();
    ApplicationState.getInstance().addCloseable(this);
  }

  @Override
  public void gameStarting(RaceData race) {}

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.joinedLobby(lobby));
    }
  }

  @Override
  public void receivedChatHistory(List<ChatMessageData> chatHistory) {}

  @Override
  public void registered(String userId) {}

  @Override
  public void receivedOpenLobbies(List<LobbyData> lobbies) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedOpenLobbies(lobbies));
    }
  }

  @Override
  public void receivedError(String message) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedError(message));
    }
  }

  @Override
  public void close() throws IOException {
    scheduler.shutdownNow();
  }

  void setObserver(LobbyBrowserObserver observer) {
    this.observer = observer;
  }

  void createdView() {
    scheduler.scheduleAtFixedRate(this::requestLobbyList, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
  }

  void leftScreen() {
    scheduler.shutdownNow();
    ApplicationState.getInstance().removeCloseable(this);
    unsubscribe();
  }

  void requestLobbyList() {
    Client client = ApplicationState.getInstance().getClient();
    client.requestLobbies();
  }

  void joinLobby(String lobbyId) {
    Client client = ApplicationState.getInstance().getClient();
    client.joinLobby(lobbyId);
  }

  private void subscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribeErrors(this);
    client.subscribe(this);
    client.subscribeLobbyUpdates(this);
  }

  private void unsubscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.unsubscribeErrors(this);
    client.unsubscribe(this);
    client.unsubscribeLobbyUpdates(this);
  }
}
