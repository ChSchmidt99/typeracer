package app.screens.finishedSingleplayer;

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
public class GameFinishedViewSingleplayer extends View {

  private static final String FXML_PATH = "view/gamefinishedSingleplayer.fxml";

  @FXML private Label firstPlace;

  @FXML private Label duration;

  @FXML private ImageView firstPlaceImage;

  @FXML private Button returnButton;

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

  void updateView(String username, String iconId, long time) throws FileNotFoundException {
    duration.setText("Time: " + time + " s");
      firstPlace.setText(username);
    firstPlaceImage.setImage(
          IconManager.iconForId(iconId).getImage());
    }
}
