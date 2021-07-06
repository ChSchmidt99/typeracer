package app.controller;

import app.elements.JoinHandler;
import app.elements.LobbyListCell;
import client.Client;
import client.ClientObserver;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;

class OpenLobbiesController extends Controller implements ClientObserver, JoinHandler {

  private static final String FXMLPATH = "view/openlobbies.fxml";
  private final Client client;
  private final String userId;

  @FXML ListView<LobbyModel> lobbylist;

  @FXML
  Button backToStartscreen;

  OpenLobbiesController(Stage stage, Client client, String userId) {
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

  private void joinLobby(String lobbyId) {
    new GameLobbyController(stage, client, userId);
    client.joinLobby(userId, lobbyId);
  }

  private void addLobbiesToList(List<LobbyModel> idList) {
    for (int i = 0; i < idList.size(); i++) {
      lobbylist.getItems().add(i, idList.get(i));
    }
  }

  @Override
  public void registered(String userId) {}

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    Platform.runLater(() -> addLobbiesToList(lobbies));
  }

  void initActions() {
    lobbylist.setCellFactory(lobbyListView -> new LobbyListCell(this));
  }

  @Override
  public void clickedJoin(String lobbyId) {
    joinLobby(lobbyId);
  }

  @FXML
  private void backToStartscreen() throws IOException {
    client.close();
    new StartscreenController(stage);
  }

}
