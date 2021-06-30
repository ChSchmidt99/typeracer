package app.controller;

import client.Client;
import client.ClientObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.RaceModel;

import java.util.List;

/*
 * Handles all gui functionality for game creation.
 */
class CreateController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/createscreen.fxml";
  private static final String USERNAME_ERROR = "Please choose a username.";
  private final Client client;
  private String userId;

  @FXML
  TextField username;

  CreateController(Stage stage, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribe(this);
  }

  @FXML
  void switchToGameLobby() {
    if (username.getText().equals("")) {
      displayError(USERNAME_ERROR);
    } else {
      new GameLobbyController(stage, client, userId);
      client.newLobby(userId);
      }
  }

  @Override
  public void registered(String userId) {
  }

  @Override
  public void receivedError(String message) {

  }

  @Override
  public void gameStarting(RaceModel race) {

  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {

  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {

  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {

  }
}
