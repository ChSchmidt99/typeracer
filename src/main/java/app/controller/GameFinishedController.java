package app.controller;

import app.model.GameFinishedModel;
import app.model.GameFinishedModelObserver;
import app.model.GameLobbyModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import protocol.LobbyData;

/** Controller for game finished screen. */
public class GameFinishedController extends Controller implements GameFinishedModelObserver {

  private static final String FXMLPATH = "view/gamefinished.fxml";

  private final GameFinishedModel model;

  /**
   * Controller for podium screen.
   *
   * @param stage where controller is hosted
   */
  GameFinishedController(Stage stage, GameFinishedModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    this.model.setObserver(this);
  }

  @Override
  public void receivedGameLobby(LobbyData lobby) {
    model.setObserver(null);
    try {
      new GameLobbyController(stage, new GameLobbyModel(lobby)).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void returnToLobby() {
    model.requestLobby();
  }
}
