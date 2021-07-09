package app.screens.start;

import app.cursom.IconPicker;
import app.screens.View;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** View for start screen. */
public class StartScreenView extends View {

  private static final String FXMLPATH = "view/startscreen.fxml";

  @FXML private TextField username;

  @FXML private GridPane baseGridPane;

  @FXML private Button singleplayerButton;

  @FXML private Button multiplayerButton;

  /**
   * Create new start screen view.
   *
   * @param stage to host view
   */
  public StartScreenView(Stage stage) {
    super(stage, FXMLPATH);
    addIconPicker();
  }

  String getUsername() {
    return username.getText();
  }

  Button getSingleplayerButton() {
    return singleplayerButton;
  }

  Button getMultiplayerButton() {
    return multiplayerButton;
  }

  private void addIconPicker() {
    IconPicker iconPicker = new IconPicker(4);
    iconPicker.setAlignment(Pos.CENTER);
    iconPicker.setHgap(40);
    iconPicker.setVgap(20);
    baseGridPane.add(iconPicker, 1, 1);
  }
}
