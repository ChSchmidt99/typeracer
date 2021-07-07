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

import java.io.IOException;

class GameLobbyController extends Controller implements LobbyObserver, ErrorObserver {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";
  private final Client client;
  private final String userId;

  @FXML CheckBox lobbyCheckbox;

  @FXML Button startButton;

  @FXML ListView<String> userlist;

  GameLobbyController(Stage stage, Client client, String userId, LobbyModel model) throws IOException {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribeLobbyUpdates(this);
    client.subscribeErrors(this);
    showLobbyModel(model);
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
    Platform.runLater(() -> displayError(message));
  }

  @Override
  public void gameStarting(RaceModel race) {
    unsubscribeAll();
    Platform.runLater(
        () -> {
          try {
            new MultiplayerController(stage, race, client, userId).show();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(() -> showLobbyModel(lobby));
  }

  private void showLobbyModel(LobbyModel lobby) {
    userlist.getItems().clear();
    userlist.getItems().addAll(lobby.players);
    this.startButton.setDisable(lobby.isRunning);
  }

  @FXML
  private void backToLobbyBrowser() {
    client.leaveLobby();
    try {
      new OpenLobbiesController(stage, client, userId).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void unsubscribeAll() {
    client.unsubscribeErrors(this);
    client.unsubscribeLobbyUpdates(this);
  }

}
