package app.controller;

import client.Client;
import client.ClientImpl;
import client.ClientObserver;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.InetAddress;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.RaceModel;

/**
 * Handles transition functionality for startscreen.
 */
public class StartscreenController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/startscreen.fxml";
  private Client client = null;

  @FXML
  TextField username;

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
      this.client = new ClientImpl(InetAddress.getByName("127.0.0.1"), 8080);
      client.registerUser(username.getText());
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
    new ServerBrowserController(stage, client, userId);
  }

  @Override
  public void receivedError(String message) {

  }

  @Override
  public void gameStarting(RaceModel race) {

  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {

  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {

  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {

  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {

  }
}
