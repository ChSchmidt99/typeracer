package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class ServerBrowserController extends Controller {

  private static final String FXMLPATH = "view/serverbrowser.fxml";
  private static final String USERNAME_ERROR = "Please choose a username.";
  private static final String ALERT_HEADER = "Username error.";

  @FXML
  TextField username;

  ServerBrowserController (Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  private void joinGame() {
    if (username.getText().equals("")) {
      displayUsernameError();
    } else {
      new GameLobbyController(stage);
    }
  }

  @FXML
  private void switchToCreateScreen() {
    if (username.getText().equals("")) {
      displayUsernameError();
    } else {
      new CreateController(stage);
    }
  }

  private void displayUsernameError() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(ALERT_HEADER);
    alert.setContentText(USERNAME_ERROR);
    alert.setHeaderText(null);
    alert.showAndWait();
  }


}
