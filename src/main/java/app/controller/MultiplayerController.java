package app.controller;

import client.Client;
import client.RaceObserver;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.TextToType;
import model.Typeracer;
import protocol.PlayerModel;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceModel;
import util.Timestamp;

/** Handles all gui functionality associated with gameplay. */
class MultiplayerController extends Controller implements RaceObserver {

  private static final String FXMLPATH = "view/multiplayer.fxml";
  Typeracer game;
  TextToType textToType2;
  Client client;
  long raceStart;
  int notifyCounter = 0;
  List<PlayerModel> players;
  HashMap<String, Slider> userProgress = new HashMap<>();

  @FXML TextFlow textToType;

  @FXML HBox enteredText;

  @FXML VBox userlist;

  public MultiplayerController(Stage stage, RaceModel race, Client client) {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribeRaceUpdates(this);
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
   * Keylistener for user input. Checks input, colors text green if correct, red if not in method
   * charLabel.
   */
  private void handleCharInput() {
    stage
        .getScene()
        .addEventHandler(
            KeyEvent.KEY_TYPED,
            event -> {
              enteredText
                  .getChildren()
                  .add(
                      charLabelCreator(
                          game.check(event.getCharacter().charAt(0)), event.getCharacter()));
              notifyInterval();
            });
  }

  /** Creates labels for user input which will be added to hbox enteredText. */
  private Label charLabelCreator(boolean charCorrect, String letter) {
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
    client.sendProgressUpdate(
        new ProgressSnapshot(
            raceStart,
            Timestamp.currentTimestamp(),
            textToType2.getCounter(),
            textToType2.getMistakeCounter()));
  }

  /*
   * Adds the user list along with progress bars and wpm to game screen.
   */
  private void setUsers() {
    System.out.println(players);
    for (PlayerModel player : players) {
      userlist.getChildren().add(userLabelCreator(player.name));
      sliderCreator(player.userId);
      userlist.getChildren().add(userProgress.get(player.userId));
    }
  }

  private Label userLabelCreator(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    return label;
  }

  private void sliderCreator(String userId) {
    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(1);
    slider.setValue(0);
    userProgress.put(userId, slider);
  }

  private void sliderUpdate(PlayerUpdate update) {
    userProgress.get(update.userId).setValue(update.percentProgress);
  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {
    for (PlayerUpdate update : updates) {
      Platform.runLater(
          () -> {
            sliderUpdate(update);
          });
    }
  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {}
}
