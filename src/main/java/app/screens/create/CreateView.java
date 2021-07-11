package app.screens.create;

import app.screens.View;
import javafx.application.Platform;
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
    lobbyName.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (isNowFocused) {
        Platform.runLater(() -> lobbyName.selectAll());
      }
    });

    lobbyName.setFocusTraversable(false);
  }

  String getLobbyName() {
    return lobbyName.getText();
  }

  void putLobbyName(String name) {
    lobbyName.setText(name);
  }

  Button getBackButton() {
    return backButton;
  }

  Button getCreateButton() {
    return createButton;
  }
}
