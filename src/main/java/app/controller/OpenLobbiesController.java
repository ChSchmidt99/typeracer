package app.controller;

import app.elements.JoinHandler;
import app.elements.LobbyListCell;
import app.model.CreateModel;
import app.model.GameLobbyModel;
import app.model.OpenLobbiesModel;
import app.model.OpenLobbiesModelObserver;
import app.model.StartScreenModel;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyData;

class OpenLobbiesController extends Controller implements JoinHandler, OpenLobbiesModelObserver {

  private static final String FXMLPATH = "view/openlobbies.fxml";

  private final OpenLobbiesModel model;

  @FXML ListView<LobbyData> lobbylist;

  @FXML Button backToStartscreen;

  OpenLobbiesController(Stage stage, OpenLobbiesModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    initListView();
    model.setObserver(this);
    model.createdView();
  }

  @Override
  public void receivedOpenLobbies(List<LobbyData> lobbies) {
    addLobbiesToList(lobbies);
  }

  @Override
  public void joinedLobby(LobbyData lobby) {
    changeToGameLobbyScreen(lobby);
  }

  @Override
  public void clickedJoin(String lobbyId) {
    model.joinLobby(lobbyId);
  }

  @FXML
  private void switchToCreateScreen() {
    try {
      model.leftScreen();
      new CreateController(stage, new CreateModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void backToStartscreen() throws IOException {
    model.leftScreen();
    new StartscreenController(stage, new StartScreenModel()).show();
  }

  private void addLobbiesToList(List<LobbyData> lobbies) {
    lobbylist.setItems(FXCollections.observableArrayList(lobbies));
  }

  private void initListView() {
    lobbylist.setCellFactory(lobbyListView -> new LobbyListCell(this));
  }

  private void changeToGameLobbyScreen(LobbyData lobby) {
    try {
      new GameLobbyController(stage, new GameLobbyModel(lobby)).show();
      model.leftScreen();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void receivedError(String message) {
    displayError(message);
  }
}
