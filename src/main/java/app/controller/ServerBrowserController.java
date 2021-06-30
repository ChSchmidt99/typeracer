package app.controller;

import client.Client;
import client.ClientObserver;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.RaceModel;

import java.util.List;

class ServerBrowserController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/serverbrowser.fxml";
  private final Client client;
  private String userId;

  @FXML
  ListView<String> lobbylist;

  ServerBrowserController(Stage stage, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribe(this);
    client.requestLobbies();
    initActions();
  }

  @FXML
  private void switchToCreateScreen() {
      new CreateController(stage, client, userId);
  }

  private void joinGame(String gameId) {
    client.registerUser(userId);
    client.joinLobby(userId, gameId);
  }

  private void addLobbiesToList(List<LobbyModel> idList) {
    for (int i = 0; i<idList.size(); i++) {
      lobbylist.getItems().add(i, idList.get(i).id);
    }

  }

  @Override
  public void registered(String userId) {
    new GameLobbyController(stage, client, userId);
  }

  @Override
  public void receivedError(String message) {

  }

  @Override
  public void gameStarting(RaceModel race) {

  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {

  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    Platform.runLater(() -> addLobbiesToList(lobbies));
  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {

  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {

  }

  public void initActions(){
    lobbylist.setOnMouseClicked(new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent arg0) {
        joinGame(lobbylist.getSelectionModel().getSelectedItems().get(0));
      }
    });
  }
}