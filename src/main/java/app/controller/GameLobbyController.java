package app.controller;

import client.Client;
import client.ClientObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.RaceModel;

import java.util.List;

class GameLobbyController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";


  @FXML
  CheckBox lobbyCheckbox;

  @FXML
  ListView<String> userlist;

  GameLobbyController(Stage stage, Client client) {
    super(stage, FXMLPATH);
    client.subscribe(this);
  }

  @FXML
  void startGame() {
    if (lobbyCheckbox.isSelected()) {
      new MultiplayerController(stage);
    } else {
      displayError(CHECKBOX_ERROR);
    }
  }

  @Override
  public void registered(String userId) {

  }

  @Override
  public void receivedError(String message) {

  }

  @Override
  public void gameStarting(RaceModel race) {

  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
      Platform.runLater(() ->
      userlist.getItems().addAll(lobby.players));
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {

  }
}
