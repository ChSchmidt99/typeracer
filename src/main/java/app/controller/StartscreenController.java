package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Handles every transition related to start screen.
 */
public class StartscreenController extends Controller {

  private final static String FXMLPATH = "gui/startscreen.fxml";

  /**
   * Constructor for StartscreenController.
   */
  public StartscreenController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  Button switchToMultiplayerLobby;


  @FXML
  private void switchToMultiplayerLobby() throws Exception {


  }
}
