package app.controller;

import app.model.GameLobbyModel;
import app.model.GameLobbyModelObserver;
import app.model.MultiplayerModel;
import app.model.OpenLobbiesModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyData;
import protocol.RaceData;
import protocol.UserData;

class GameLobbyController extends Controller implements GameLobbyModelObserver {

  private static final String FXMLPATH = "view/gamelobby.fxml";
  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";

  private final GameLobbyModel model;

  @FXML CheckBox lobbyCheckbox;

  @FXML Button startButton;

  @FXML ListView<String> userlist;

  GameLobbyController(Stage stage, GameLobbyModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    this.model.setObserver(this);
    displayLobby(model.getLobby());
    model.setReady(lobbyCheckbox.isSelected());
  }

  @FXML
  void checkedReady() {
    model.setReady(lobbyCheckbox.isSelected());
  }

  @FXML
  void startGame() {
    if (lobbyCheckbox.isSelected()) {
      model.startRace();
    } else {
      displayError(CHECKBOX_ERROR);
    }
  }

  @FXML
  void backToLobbyBrowser() {
    model.leaveLobby();
    try {
      new OpenLobbiesController(stage, new OpenLobbiesModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updatedLobby() {
    displayLobby(model.getLobby());
  }

  @Override
  public void startedRace(RaceData race) {
    try {
      new MultiplayerController(stage, new MultiplayerModel(race)).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void displayLobby(LobbyData lobby) {
    userlist.getItems().clear();
    List<String> items = new ArrayList<>();
    for (UserData user : lobby.players) {
      items.add(toListItem(user));
    }
    userlist.getItems().addAll(items);
    this.startButton.setDisable(lobby.isRunning);
  }

  private String toListItem(UserData userData) {
    String skeleton = "%s (%s)";
    return String.format(skeleton, userData.name, userData.state);
  }
}
