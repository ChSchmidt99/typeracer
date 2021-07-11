package app.screens.create;

import app.screens.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** View for Create Screen. */
public class CreateView extends View {

  private static final String FXML_PATH = "view/createscreen.fxml";

  @FXML private TextField lobbyName;

  @FXML private Button createButton;

  @FXML private Button backButton;

  /**
   * Create new create view.
   *
   * @param stage to host view
   */
  public CreateView(Stage stage) {
    super(stage, FXML_PATH);
    lobbyName.setFocusTraversable(false);
  }

  String getLobbyName() {
    return lobbyName.getText();
  }

  Button getBackButton() {
    return backButton;
  }

  Button getCreateButton() {
    return createButton;
  }
}
