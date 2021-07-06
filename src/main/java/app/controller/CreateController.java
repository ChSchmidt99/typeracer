package app.controller;

import app.Icon;
import app.IconManager;
import client.Client;
import client.ClientObserver;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.LobbyModel;

/*
 * Handles all gui functionality for game creation.
 */
class CreateController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/createscreen.fxml";
  private static final String LOBBY_NAME_ERROR = "Please enter lobby name";
  private final Client client;
  private String userId;

  @FXML TextField lobbyname;

  @FXML Button backToLobbyBrowser;

  CreateController(Stage stage, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribe(this);
  }

  @FXML
  void switchToGameLobby() {
    if (lobbyname.getText().equals("")) {
      displayError(LOBBY_NAME_ERROR);
    } else {
      new GameLobbyController(stage, client, userId);
      client.newLobby(userId, lobbyname.getText(), IconManager.getSelectedIcon().getId());
    }
  }

  @Override
  public void registered(String userId) {}

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {}

  @FXML
  void backToLobbyBrowser() {
    new OpenLobbiesController(stage, client, userId);
  }
}
