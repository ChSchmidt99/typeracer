package app.controller;

import client.Client;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/** Controller for game finished screen. */
public class GameFinishedController extends Controller {

  private final Client client;
  private final String userId;

  private static final String FXMLPATH = "view/gamefinished.fxml";

  /**
   * Controller for podium screen.
   *
   * @param stage where controller is hosted
   * @param client for server communication
   * @param userId id of user
   */
  GameFinishedController(Stage stage, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
  }

  // TODO: REQUEST LOBBY FROM SERVER
  @FXML
  private void returnToLobby() {
    new GameLobbyController(stage, client, userId);
  }
}
