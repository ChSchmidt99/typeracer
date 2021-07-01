package app.controller;

import client.Client;
import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.RaceModel;

class GameLobbyController extends Controller implements LobbyObserver, ErrorObserver {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";
  private final Client client;

  @FXML CheckBox lobbyCheckbox;

  @FXML Button startButton;

  @FXML ListView<String> userlist;

  GameLobbyController(Stage stage, Client client) {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribeLobbyUpdates(this);
    client.subscribeErrors(this);
  }

  @FXML
  void checkedReady() {
    client.setIsReady(lobbyCheckbox.isSelected());
  }

  @FXML
  void startGame() {
    if (lobbyCheckbox.isSelected()) {
      client.startRace();
    } else {
      displayError(CHECKBOX_ERROR);
    }
  }

  @Override
  public void receivedError(String message) {
    Platform.runLater(
        () -> {
          displayError(message);
        });
  }

  @Override
  public void gameStarting(RaceModel race) {
    Platform.runLater(() -> new MultiplayerController(stage, race, client));
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(
        () -> {
          userlist.getItems().clear();
          userlist.getItems().addAll(lobby.players);
          this.startButton.setDisable(lobby.isRunning);
        });
  }
}
