package app.controller;

import app.model.CreateModel;
import app.model.CreateModelObserver;
import app.model.GameLobbyModel;
import app.model.OpenLobbiesModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.LobbyData;

/*
 * Handles all gui functionality for game creation.
 */
class CreateController extends Controller implements CreateModelObserver {

  private static final String FXMLPATH = "view/createscreen.fxml";
  private static final String LOBBY_NAME_ERROR = "Please enter lobby name";

  private final CreateModel model;

  @FXML TextField lobbyName;

  @FXML Button backToLobbyBrowser;

  CreateController(Stage stage, CreateModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    model.setObserver(this);
  }

  @FXML
  void switchToGameLobby() {
    if (lobbyName.getText().equals("")) {
      displayError(LOBBY_NAME_ERROR);
    } else {
      model.createLobby(lobbyName.getText());
    }
  }

  @FXML
  void backToLobbyBrowser() {
    try {
      new OpenLobbiesController(stage, new OpenLobbiesModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void joinedLobby(LobbyData lobby) {
    try {
      new GameLobbyController(stage, new GameLobbyModel(lobby)).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
