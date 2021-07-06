package app.controller;

import app.IconManager;
import app.elements.IconPicker;
import client.Client;
import client.ClientImpl;
import client.ClientObserver;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import protocol.LobbyModel;

/** Handles transition functionality for startscreen. */
public class StartscreenController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/startscreen.fxml";
  private static final String USERNAME_ERROR = "Please choose a username";
  private Client client = null;

  @FXML TextField username;

  @FXML GridPane baseGridPane;

  /**
   * Constructor for StartscreenController; creates a new Startscreen.
   *
   * @param stage JavaFx stage to host the view in
   */
  public StartscreenController(Stage stage) {
    super(stage, FXMLPATH);
    IconManager iconManager = new IconManager();
    IconPicker iconPicker = new IconPicker(4);
    iconPicker.setAlignment(Pos.CENTER);
    iconPicker.setHgap(40);
    iconPicker.setVgap(20);
    baseGridPane.add(iconPicker, 0, 3);
  }

  @FXML
  private void switchToLobbyBrowser() {
    try {
      if (username.getText().equals("")) {
        displayError(USERNAME_ERROR);
      } else {
        this.client = new ClientImpl(InetAddress.getByName("127.0.0.1"), 8080);
        client.subscribe(this);
        client.registerUser(username.getText());
        stage.setOnCloseRequest(
            new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent event) {
                Platform.exit();
                try {
                  client.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void switchToSingleplayer() {
    new SingleplayerController(stage);
  }

  @Override
  public void registered(String userId) {
    Platform.runLater(
        () -> {
          new OpenLobbiesController(stage, client, userId);
        });
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {}
}
