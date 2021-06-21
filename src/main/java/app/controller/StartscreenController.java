package app.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Handles transition functionality for startscreen.
 */
public class StartscreenController extends Controller {

  private static final String FXMLPATH = "view/startscreen.fxml";

  /**
   * Constructor for StartscreenController; creates a new Startscreen.
   *
   * @param stage JavaFx stage to host the view in
   */
  public StartscreenController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  private void switchToServerBrowser() {
    new ServerBrowserController(stage);
  }

  @FXML
  private void switchToSingleplayer() {
    new SingleplayerController(stage);
  }
}
