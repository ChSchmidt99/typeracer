package app.model;

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
public class OpenLobbiesModel implements LobbyObserver, ClientObserver, ErrorObserver, Closeable {

  /** Update interval in seconds. */
  private static final long UPDATE_INTERVAL = 1;

  private OpenLobbiesModelObserver observer;
  private final ScheduledExecutorService scheduler;

  /** Model for OpenLobbies view. */
  public OpenLobbiesModel() {
    scheduler = Executors.newScheduledThreadPool(1);
    subscribe();
    ApplicationState.getInstance().addCloseable(this);
  }

  /**
   * Set Observer.
   *
   * @param observer observer
   */
  public void setObserver(OpenLobbiesModelObserver observer) {
    this.observer = observer;
  }

  /** Call when view was created to start automatic lobby refreshing. */
  public void createdView() {
    scheduler.scheduleAtFixedRate(this::requestLobbyList, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
  }

  /** Call on scene change. */
  public void leftScreen() {
    scheduler.shutdownNow();
    ApplicationState.getInstance().removeCloseable(this);
    unsubscribe();
  }

  /** Request list of all open lobbies. */
  public void requestLobbyList() {
    Client client = ApplicationState.getInstance().getClient();
    client.requestLobbies();
  }

  /**
   * Call to join lobby.
   *
   * @param lobbyId id of lobby
   */
  public void joinLobby(String lobbyId) {
    Client client = ApplicationState.getInstance().getClient();
    client.joinLobby(lobbyId);
  }

  @Override
  public void gameStarting(RaceData race) {
    // TODO: remove unused functions from interface
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    if (observer != null) {
      Platform.runLater(() -> observer.joinedLobby(lobby));
    }
  }

  @Override
  public void receivedChatHistory(List<ChatMessageData> chatHistory) {}

  @Override
  public void registered(String userId) {
    // TODO: remove unused functions from interface
  }

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

  @Override
  public void close() throws IOException {
    scheduler.shutdownNow();
  }
}
