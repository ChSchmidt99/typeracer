package app.controller;

import app.ApplicationState;
import app.elements.IconPicker;
import app.model.OpenLobbiesModel;
import app.model.StartScreenModel;
import app.model.StartScreenModelObserver;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/** Handles transition functionality for startscreen. */
public class StartscreenController extends Controller implements StartScreenModelObserver {

  private static final String FXMLPATH = "view/startscreen.fxml";
  private static final String USERNAME_ERROR = "Please choose a username";

  private final StartScreenModel model;

  @FXML TextField username;

  @FXML GridPane baseGridPane;

  /**
   * Constructor for StartscreenController; creates a new Startscreen.
   *
   * @param stage JavaFx stage to host the view in
   */
  public StartscreenController(Stage stage, StartScreenModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    model.setObserver(this);
    addIconPicker();
  }

  @FXML
  private void switchToLobbyBrowser() {
    if (ApplicationState.getInstance().getClient() == null) {
      try {
        ApplicationState.getInstance().newClient();
        if (username.getText().equals("")) {
          displayError(USERNAME_ERROR);
        } else {
          model.register(username.getText());
        }
      } catch (IOException e) {
        displayError(e.getMessage());
      }
    }
  }

  @FXML
  private void switchToSingleplayer() {
    try {
      new SingleplayerController(stage).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addIconPicker() {
    IconPicker iconPicker = new IconPicker(4);
    iconPicker.setAlignment(Pos.CENTER);
    iconPicker.setHgap(40);
    iconPicker.setVgap(20);
    baseGridPane.add(iconPicker, 1, 1);
  }

  /** Called when user was registered successfully. */
  public void registered() {
    try {
      new OpenLobbiesController(stage, new OpenLobbiesModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void receivedError(String message) {
    displayError(message);
  }
}
