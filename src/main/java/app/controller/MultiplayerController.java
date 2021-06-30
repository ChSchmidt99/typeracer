package app.controller;

import client.Client;
import client.ClientObserver;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.TextToType;
import model.Typeracer;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceModel;
import util.Timestamp;

/**
 * Handles all gui functionality associated with gameplay.
 */
class MultiplayerController extends Controller implements ClientObserver {

  private static final String FXMLPATH = "view/multiplayer.fxml";
  Typeracer game;
  TextToType textToType2;
  Client client;
  long raceStart;
  int notifyCounter = 0;
  List<String> players;

  @FXML
  TextFlow textToType;

  @FXML
  HBox enteredText;

  @FXML
  VBox userlist;

  public MultiplayerController(Stage stage, RaceModel race, Client client) {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribe(this);
    this.game = new Typeracer(race.textToType);
    this.players = race.players;
    this.textToType2 = game.getState().getTypeChar();
    Text text = new Text(game.getState().getTypeChar().getCompleteText());
    textToType.getChildren().addAll(text);
    handleCharInput();
    this.raceStart = Timestamp.currentTimestamp();
    setUsers();
  }

  /**
   * Keylistener for user input.
   * Checks input, colors text green if correct, red if not in method charLabel.
   */
  private void handleCharInput() {
    stage.getScene().addEventHandler(
        KeyEvent.KEY_TYPED,
        event -> {
            enteredText.getChildren().add(charLabel(game.check(event.getCharacter().charAt(0)), event.getCharacter()));
            notifyInterval();
          }
    );
  }

  /**
   * Creates labels for user input which will be added to hbox enteredText.
   */
  private Label charLabel(boolean charCorrect, String letter) {
    Label label = new Label(letter);
    if (charCorrect) {
      label.setTextFill(Color.GREEN);
    } else {
      label.setTextFill(Color.RED);
    }
    return label;
  }

  /*
   * Limits the interval, in which the server gets notified about changes.
   */
  private void notifyInterval() {
    notifyCounter++;
    if (notifyCounter == 5) {
      notifyServer();
      notifyCounter = 0;
    }
  }

  private void notifyServer() {
    client.sendProgressUpdate(new ProgressSnapshot(raceStart, Timestamp.currentTimestamp(), textToType2.getCounter(), textToType2.getMistakeCounter()));
  }

  /*
   * Adds the user list along with progress bars and wpm to game screen.
   */
  private void setUsers() {
    System.out.println(players);
    for (String player : players) {
      userlist.getChildren().add(userLabel(player));
      userlist.getChildren().add(new ProgressBar());
    }
  }

  private Label userLabel(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    return label;
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