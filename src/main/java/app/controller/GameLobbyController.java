package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

class GameLobbyController extends Controller {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";

  @FXML
  CheckBox lobbyCheckbox;

  GameLobbyController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  void startGame() {
    if (lobbyCheckbox.isSelected()) {
      new MultiplayerController(stage);
    } else {
      displayError(CHECKBOX_ERROR);
    }
  }
}
