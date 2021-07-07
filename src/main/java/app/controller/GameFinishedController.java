package app.controller;

import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.RaceModel;

import java.io.IOException;

/** Controller for game finished screen. */
public class GameFinishedController extends Controller implements LobbyObserver, ErrorObserver {

  private final Client client;
  private final String userId;

  private static final String FXMLPATH = "view/gamefinished.fxml";

  /**
   * Controller for podium screen.
   *
   * @param stage where controller is hosted
   * @param client for server communication
   * @param userId id of user
   */
  GameFinishedController(Stage stage, Client client, String userId) throws IOException {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribeErrors(this);
    client.subscribeLobbyUpdates(this);
    this.userId = userId;
  }

  @FXML
  private void returnToLobby() {
    client.requestLobbyUpdate();
  }

  @Override
  public void gameStarting(RaceModel race) {}

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(() -> {changeToGameLobbyScreen(lobby);});
  }

  @Override
  public void receivedError(String message) {
    Platform.runLater(() -> displayError(message));
  }

  private void changeToGameLobbyScreen(LobbyModel lobby) {
    try {
      new GameLobbyController(stage, client, userId, lobby).show();
      unsubscribeAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void unsubscribeAll() {
    client.unsubscribeErrors(this);
    client.unsubscribeLobbyUpdates(this);
  }

}
