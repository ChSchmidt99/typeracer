package app.controller;

import app.IconManager;
import app.model.GameFinishedModel;
import app.model.GameFinishedModelObserver;
import app.model.GameLobbyModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import protocol.LobbyData;

/** Controller for game finished screen. */
public class GameFinishedController extends Controller implements GameFinishedModelObserver {

  private static final String FXMLPATH = "view/gamefinished.fxml";

  private final GameFinishedModel model;

  @FXML Label firstPlace;

  @FXML Label secondPlace;

  @FXML Label thirdPlace;

  @FXML ImageView firstPlaceImage;

  @FXML ImageView secondPlaceImage;

  @FXML ImageView thirdPlaceImage;

  /**
   * Controller for podium screen.
   *
   * @param stage where controller is hosted
   */
  GameFinishedController(Stage stage, GameFinishedModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    this.model.setObserver(this);
    setPlacementOrder();
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

  private void setPlacementOrder() throws FileNotFoundException {

    firstPlace.setText(model.getRaceResult().classification.get(0).userData.name);
    firstPlaceImage.setImage(
        IconManager.iconForId(model.getRaceResult().classification.get(0).userData.iconId)
            .getImage());

    if (model.getRaceResult().classification.size() >= 2) {
      secondPlace.setText(model.getRaceResult().classification.get(1).userData.name);
      secondPlaceImage.setImage(
          IconManager.iconForId(model.getRaceResult().classification.get(1).userData.iconId)
              .getImage());
    }

    if (model.getRaceResult().classification.size() == 3) {
      thirdPlace.setText(model.getRaceResult().classification.get(2).userData.name);
      thirdPlaceImage.setImage(
          IconManager.iconForId(model.getRaceResult().classification.get(2).userData.iconId)
              .getImage());
    }
  }

  @FXML
  private void returnToLobby() {
    model.requestLobby();
  }

  @Override
  public void receivedError(String message) {
    displayError(message);
  }
}
