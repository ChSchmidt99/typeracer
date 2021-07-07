package app.controller;

import app.IconManager;
import client.Client;
import client.ClientObserver;

import java.io.IOException;
import java.util.List;

import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.RaceModel;

/*
 * Handles all gui functionality for game creation.
 */
class CreateController extends Controller implements ClientObserver, LobbyObserver, ErrorObserver {

  private static final String FXMLPATH = "view/createscreen.fxml";
  private static final String LOBBY_NAME_ERROR = "Please enter lobby name";
  private final Client client;
  private final String userId;

  @FXML TextField lobbyname;

  @FXML Button backToLobbyBrowser;

  CreateController(Stage stage, Client client, String userId) throws IOException {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribe(this);
    client.subscribeLobbyUpdates(this);
    client.subscribeErrors(this);
  }

  @FXML
  void switchToGameLobby() {
    if (lobbyname.getText().equals("")) {
      displayError(LOBBY_NAME_ERROR);
    } else {
      client.newLobby(userId, lobbyname.getText(), IconManager.getSelectedIcon().getId());
    }
  }

  @Override
  public void registered(String userId) {}

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {}

  @FXML
  void backToLobbyBrowser() {
    try{
      new OpenLobbiesController(stage, client, userId).show();
      unsubscribeAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void gameStarting(RaceModel race) {}

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(() -> changeToGameLobbyScreen(lobby));
  }

  @Override
  public void receivedError(String message) {
    Platform.runLater(() -> displayError(message));
  }

  private void changeToGameLobbyScreen(LobbyModel lobby) {
    unsubscribeAll();
    try {
      new GameLobbyController(stage, client, userId, lobby).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void unsubscribeAll() {
    client.subscribe(this);
    client.subscribeLobbyUpdates(this);
    client.subscribeErrors(this);
  }
}
