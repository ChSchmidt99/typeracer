package app.model;

import app.ApplicationState;
import client.Client;
import client.LobbyObserver;
import javafx.application.Platform;
import protocol.LobbyModel;
import protocol.RaceModel;

public class GameLobbyModel implements LobbyObserver {

  private LobbyModel lobby;

  private GameLobbyModelObserver observer;

  public GameLobbyModel(LobbyModel lobby) {
    this.lobby = lobby;
    ApplicationState.getInstance().getClient().subscribeLobbyUpdates(this);
  }

  public void setObserver(GameLobbyModelObserver observer) {
    this.observer = observer;
  }

  public LobbyModel getLobby() {
    return lobby;
  }

  public void leaveLobby() {
    Client client = ApplicationState.getInstance().getClient();
    client.leaveLobby();
    client.unsubscribeLobbyUpdates(this);
  }

  public void setReady(boolean isReady) {
    System.out.println("Set Ready: " + isReady);
    Client client = ApplicationState.getInstance().getClient();
    client.setIsReady(isReady);
  }

  public void startRace() {
    System.out.println("Start Race");
    Client client = ApplicationState.getInstance().getClient();
    client.startRace();
  }

  @Override
  public void gameStarting(RaceModel race) {
    System.out.println("Race starting");
    if (observer != null) {
      Platform.runLater(() -> observer.startedRace(race));
    }
    ApplicationState.getInstance().getClient().unsubscribeLobbyUpdates(this);
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    this.lobby = lobby;
    if (observer != null) {
      Platform.runLater(() -> observer.updatedLobby());
    }
  }
}
