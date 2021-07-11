package app.screens.finishedsingleplayer;

import app.IconManager;
import app.screens.View;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/** View for Game finished Screen. */
public class GameFinishedViewSingleplayer extends View {

  private static final String FXML_PATH = "view/gamefinishedSingleplayer.fxml";

  @FXML private Label firstPlace;

  @FXML private Label duration;

  @FXML private ImageView firstPlaceImage;

  @FXML private Button returnButton;

  @FXML private Label stats;

  /**
   * Create new game finished view.
   *
   * @param stage to host view
   */
  public GameFinishedViewSingleplayer(Stage stage) {
    super(stage, FXML_PATH);
  }

  Button getReturnButton() {
    return returnButton;
  }

  void updateView(String username, String iconId, long time, int wpm, double acc)
      throws FileNotFoundException {
    duration.setText("Time: " + time + " s");
    String result = "WPM: %d, Acc: %.2f%%";
    String resultFormatted = String.format(result, wpm, acc);
    stats.setText(resultFormatted);
    firstPlace.setText(username);
    firstPlaceImage.setImage(IconManager.iconForId(iconId).getImage());
  }
}
