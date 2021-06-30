package app.controller;

import client.Client;
import client.ClientObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.RaceModel;

import java.util.List;

class GameLobbyController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";
  private final Client client;

  @FXML
  CheckBox lobbyCheckbox;

  @FXML
  ListView<String> userlist;

  GameLobbyController(Stage stage, Client client) {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribe(this);
  }

   //TODO checkbox error handling
  @FXML
  void startGame() {
    if (lobbyCheckbox.isSelected()) {
      client.setIsReady(true);
      client.startRace();
    } else {
      displayError(CHECKBOX_ERROR);
    }
  }

  @Override
  public void registered(String userId) {

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
    Platform.runLater(() ->
    new MultiplayerController(stage, race, client)
    );
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(() ->
            userlist.getItems().addAll(lobby.players));
  }
  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {

  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {

  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {

  }
}