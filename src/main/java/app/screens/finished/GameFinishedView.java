package app.screens.finished;

import app.IconManager;
import app.screens.View;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import protocol.RaceResult;

/** View for Game finished Screen. */
public class GameFinishedView extends View {

  private static final String FXML_PATH = "view/gamefinished.fxml";

  @FXML private Label firstPlace;

  @FXML private Label secondPlace;

  @FXML private Label thirdPlace;

  @FXML private Label duration;

  @FXML private ImageView firstPlaceImage;

  @FXML private ImageView secondPlaceImage;

  @FXML private ImageView thirdPlaceImage;

  @FXML private Button returnButton;

  /**
   * Create new game finished view.
   *
   * @param stage to host view
   */
  public GameFinishedView(Stage stage) {
    super(stage, FXML_PATH);

  }

  Button getReturnButton() {
    return returnButton;
  }

  void updateView(RaceResult result) throws FileNotFoundException {
    duration.setText("Time: " + result.duration + " s");
    firstPlace.setText(result.classification.get(0).userData.name);
    firstPlaceImage.setImage(
        IconManager.iconForId(result.classification.get(0).userData.iconId).getImage());

    if (result.classification.size() >= 2) {
      secondPlace.setText(result.classification.get(1).userData.name);
      secondPlaceImage.setImage(
          IconManager.iconForId(result.classification.get(1).userData.iconId).getImage());
    }

    if (result.classification.size() == 3) {
      thirdPlace.setText(result.classification.get(2).userData.name);
      thirdPlaceImage.setImage(
          IconManager.iconForId(result.classification.get(2).userData.iconId).getImage());
    }
  }
}
