package app.controller;

import client.Client;
import client.RaceObserver;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class GameFinishedController extends Controller {

  private Client client;
  private String userId;

  private static final String FXMLPATH = "view/gamefinished.fxml";

  GameFinishedController(Stage stage, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
  }

  //TODO: REQUEST LOBBY FROM SERVER
  @FXML
  private void returnToLobby() {
    new GameLobbyController(stage, client, userId);
  }
}
