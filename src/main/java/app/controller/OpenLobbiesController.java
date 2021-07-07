package app.controller;

import app.ApplicationState;
import app.elements.JoinHandler;
import app.elements.LobbyListCell;
import app.model.CreateModel;
import app.model.GameLobbyModel;
import app.model.OpenLobbiesModel;
import app.model.OpenLobbiesModelObserver;
import app.model.StartScreenModel;
import client.Client;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyModel;

class OpenLobbiesController extends Controller implements JoinHandler, OpenLobbiesModelObserver {

  private static final String FXMLPATH = "view/openlobbies.fxml";

  private OpenLobbiesModel model;

  @FXML ListView<LobbyModel> lobbylist;

  @FXML Button backToStartscreen;

  OpenLobbiesController(Stage stage, OpenLobbiesModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    initActions();
    model.setObserver(this);
    model.requestLobbyList();
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    addLobbiesToList(lobbies);
  }

  @Override
  public void joinedLobby(LobbyModel lobby) {
    changeToGameLobbyScreen(lobby);
  }

  @Override
  public void clickedJoin(String lobbyId) {
    model.joinLobby(lobbyId);
  }

  @FXML
  private void switchToCreateScreen() {
    try {
      new CreateController(stage, new CreateModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void backToStartscreen() throws IOException {
    Client client = ApplicationState.getInstance().getClient();
    client.close();
    ApplicationState.getInstance().setClient(null);
    new StartscreenController(stage, new StartScreenModel()).show();
  }

  private void addLobbiesToList(List<LobbyModel> idList) {
    for (int i = 0; i < idList.size(); i++) {
      lobbylist.getItems().add(i, idList.get(i));
    }
  }

  private void initActions() {
    lobbylist.setCellFactory(lobbyListView -> new LobbyListCell(this));
  }

  private void changeToGameLobbyScreen(LobbyModel lobby) {
    try {
      new GameLobbyController(stage, new GameLobbyModel(lobby)).show();
      model.setObserver(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
