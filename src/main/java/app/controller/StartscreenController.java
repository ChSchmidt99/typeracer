package app.controller;

import client.ClientImpl;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.InetAddress;

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
    try {
      new ServerBrowserController(stage, new ClientImpl(InetAddress.getByName("127.0.0.1"), 8080));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void switchToSingleplayer() {
    new SingleplayerController(stage);
  }
}
