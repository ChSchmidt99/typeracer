package app.screens.createSingleplayer;

import app.screens.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CreateSingleplayerView extends View {

  private static final String FXML_PATH = "view/createsingleplayer.fxml";

  @FXML private Button startButton;

  @FXML private Button backButton;

  @FXML private Label username;

  @FXML private ImageView usericon;

  /**
   * Create new create view for Singleplayer.
   *
   * @param stage to host view
   */
  public CreateSingleplayerView(Stage stage) {
    super(stage, FXML_PATH);
  }

  Label getUsernameLabel() {
    return username;
  }

  ImageView getUsericon() {
    usericon.setFitHeight(50);
    return usericon;
  }

  Button getBackButton() {
    return backButton;
  }

  Button getStartButton() {
    return startButton;
  }
}
