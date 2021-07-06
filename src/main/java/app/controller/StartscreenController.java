package app.controller;

import client.Client;
import client.ClientImpl;
import client.ClientObserver;
import java.net.InetAddress;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.LobbyModel;

/** Handles transition functionality for startscreen. */
public class StartscreenController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/startscreen.fxml";
  private static final String USERNAME_ERROR = "Please choose a username";
  private Client client = null;

  @FXML TextField username;

  /**
   * Constructor for StartscreenController; creates a new Startscreen.
   *
   * @param stage JavaFx stage to host the view in
   */
  public StartscreenController(Stage stage) {
    super(stage, FXMLPATH);
  }

  @FXML
  private void switchToLobbyBrowser() {
    try {
      if (username.getText().equals("")) {
        displayError(USERNAME_ERROR);
      } else {
        this.client = new ClientImpl(InetAddress.getByName("127.0.0.1"), 8080);
        client.subscribe(this);
        client.registerUser(username.getText());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void switchToSingleplayer() {
    new SingleplayerController(stage);
  }

  @Override
  public void registered(String userId) {
    Platform.runLater(
        () -> {
          new OpenLobbiesController(stage, client, userId);
        });
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {}
}
