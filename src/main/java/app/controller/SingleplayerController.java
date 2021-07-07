package app.controller;

import java.io.IOException;
import javafx.stage.Stage;

class SingleplayerController extends Controller {

  private static final String FXMLPATH = "view/singleplayer.fxml";

  SingleplayerController(Stage stage) throws IOException {
    super(stage, FXMLPATH);
  }
}
