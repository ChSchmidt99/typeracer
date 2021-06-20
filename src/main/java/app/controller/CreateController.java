package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class CreateController extends Controller {

  private static final String FXMLPATH = "view/createscreen.fxml";
  private static final String SERVERNAME_ERROR = "Please choose a servername.";

  @FXML
  TextField servername;

  CreateController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  void switchToGameLobby() {
    if (servername.getText().equals("")) {
      displayError(SERVERNAME_ERROR);
    } else {
      new GameLobbyController(stage);
    }
  }

}
