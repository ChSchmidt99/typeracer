package app.controller;

import javafx.stage.Stage;

class GameLobbyController extends Controller {

  private static final String FXMLPATH = "view/gamelobby.fxml";

  GameLobbyController(Stage stage) {
    super(stage, FXMLPATH);
  }
}
