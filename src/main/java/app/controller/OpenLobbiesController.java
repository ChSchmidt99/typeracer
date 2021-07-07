package app.controller;

import app.IconManager;
import app.elements.JoinHandler;
import app.elements.LobbyListCell;
import client.Client;
import client.ClientObserver;
import java.io.IOException;
import java.util.List;

import client.ErrorObserver;
import client.LobbyObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;
import protocol.RaceModel;

class OpenLobbiesController extends Controller implements ClientObserver, JoinHandler, LobbyObserver, ErrorObserver {

  private static final String FXMLPATH = "view/openlobbies.fxml";
  private final Client client;
  private final String userId;

  @FXML ListView<LobbyModel> lobbylist;

  @FXML Button backToStartscreen;

  OpenLobbiesController(Stage stage, Client client, String userId) throws IOException {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
    client.subscribe(this);
    client.subscribeErrors(this);
    client.subscribeLobbyUpdates(this);
    client.requestLobbies();
    initActions();
  }

  @FXML
  private void switchToCreateScreen() {
    try {
      unsubscribeAll();
      new CreateController(stage, client, userId).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void joinLobby(String lobbyId) {
    client.joinLobby(userId, lobbyId, IconManager.getSelectedIcon().getId());
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
    new StartscreenController(stage).show();
  }

  @Override
  public void gameStarting(RaceModel race) {}

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    Platform.runLater(() -> changeToGameLobbyScreen(lobby));
  }

  @Override
  public void receivedError(String message) {
    Platform.runLater(() -> displayError(message));
  }

  private void changeToGameLobbyScreen(LobbyModel lobby) {
    try {
      new GameLobbyController(stage, client, userId, lobby).show();
      unsubscribeAll();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void unsubscribeAll() {
    client.unsubscribe(this);
    client.unsubscribeErrors(this);
    client.unsubscribeLobbyUpdates(this);
  }

}
