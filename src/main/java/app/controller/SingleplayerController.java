package app.controller;

import javafx.stage.Stage;

import java.io.IOException;

class SingleplayerController extends Controller {

  private static final String FXMLPATH = "view/singleplayer.fxml";

  SingleplayerController(Stage stage) throws IOException {
    super(stage, FXMLPATH);
  }
}
