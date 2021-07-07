package app.model;

import app.ApplicationState;
import client.Client;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyData;
import protocol.RaceData;

/** Model for GameLobby View. */
public class GameLobbyModel implements LobbyObserver {

  private LobbyData lobby;

  private GameLobbyModelObserver observer;

  public GameLobbyModel(LobbyData lobby) {
    this.lobby = lobby;
    ApplicationState.getInstance().getClient().subscribeLobbyUpdates(this);
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
    client.unsubscribeLobbyUpdates(this);
  }

  /**
   * Notify server if player is ready or not.
   *
   * @param isReady whether or not player is ready
   */
  public void setReady(boolean isReady) {
    System.out.println("Set Ready: " + isReady);
    Client client = ApplicationState.getInstance().getClient();
    client.setIsReady(isReady);
  }

  /** Call to start the race. */
  public void startRace() {
    System.out.println("Start Race");
    Client client = ApplicationState.getInstance().getClient();
    client.startRace();
  }

  @Override
  public void gameStarting(RaceData race) {
    System.out.println("Race starting");
    if (observer != null) {
      Platform.runLater(() -> observer.startedRace(race));
    }
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
  }

  @Override
  public void receivedLobbyUpdate(LobbyData lobby) {
    this.lobby = lobby;
    if (observer != null) {
      Platform.runLater(() -> observer.updatedLobby());
    }
  }
}
