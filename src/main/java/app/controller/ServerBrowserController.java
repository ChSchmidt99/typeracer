package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class ServerBrowserController extends Controller {

  private static final String FXMLPATH = "view/serverbrowser.fxml";
  private static final String USERNAME_ERROR = "Please choose a username.";

  @FXML
  TextField username;

  ServerBrowserController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  private void joinGame() {
    if (username.getText().equals("")) {
      displayError(USERNAME_ERROR);
    } else {
      new GameLobbyController(stage);
    }
  }

  @FXML
  private void switchToCreateScreen() {
    if (username.getText().equals("")) {
      displayError(USERNAME_ERROR);
    } else {
      new CreateController(stage);
    }
  }
}
