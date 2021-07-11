package app.screens.finished;

import app.IconManager;
import app.screens.View;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import protocol.RaceResult;
import protocol.UserResult;

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
    String resultText = "%s (WPM: %d Acc: %.2f%%)";
    List<String> outputs = new ArrayList<>();
    for (UserResult user : result.classification) {
      Double acc = (1.0 - (double) user.mistakes / (double) result.text.length()) * 100;
      outputs.add(String.format(resultText, user.userData.name, user.wpm, acc));
    }

    if (outputs.size() >= 1) {
      firstPlace.setText(outputs.get(0));
      firstPlaceImage.setImage(
          IconManager.iconForId(result.classification.get(0).userData.iconId).getImage());
    }
    if (outputs.size() >= 2) {
      firstPlace.setText(outputs.get(1));
      secondPlaceImage.setImage(
          IconManager.iconForId(result.classification.get(1).userData.iconId).getImage());
    }
    if (outputs.size() >= 3) {
      firstPlace.setText(outputs.get(2));
      thirdPlaceImage.setImage(
          IconManager.iconForId(result.classification.get(2).userData.iconId).getImage());
    }
  }
}
