package app.controller;

import app.IconManager;
import client.Client;
import client.RaceObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import model.CheckResult;
import model.GamePhase;
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

  @FXML Label checkeredFlagLabel;

  public MultiplayerController(Stage stage, RaceModel race, Client client) {
    super(stage, FXMLPATH);
    this.client = client;
    client.subscribeRaceUpdates(this);
    this.game = new Typeracer(race.textToType);
    this.players = race.players;
    this.textToType2 = game.getState().getTypeChar();
    Text text = new Text(game.getState().getTypeChar().getCompleteText());
    text.setFill(Color.web("#62fbf7"));
    textToType.getChildren().addAll(text);
    setupKeyHandler();
    this.raceStart = Timestamp.currentTimestamp();
    setUsers();
  }

  /**
   * Keylistener for user input. Checks input, colors text green if correct, red if not in method
   * charLabel.
   */
  private void setupKeyHandler() {
    stage
        .getScene()
        .addEventHandler(
            KeyEvent.KEY_TYPED,
            event -> {
              if (game.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
                return;
              }
              String typed = event.getCharacter();
              CheckResult check = game.check(typed.charAt(0));
              enteredText.getChildren().add(charLabelCreator(check));
              notifyInterval();
            });
  }

  /** Creates labels for user input which will be added to hbox enteredText. */
  private Label charLabelCreator(CheckResult check) {
    Label label = new Label();
    switch (check.state) {
      case CORRECT:
        label.setTextFill(Color.GREEN);
        label.setText(Character.toString(check.expected));
        break;
      case INCORRECT:
        label.setTextFill(Color.RED);
        label.setText(Character.toString(check.typed));
        break;
      case AUTOCORRECTED:
        label.setTextFill(Color.BLUE);
        label.setText(Character.toString(check.expected));
        break;
      default:
    }
    return label;
  }

  /*
   * Limits the interval, in which the server gets notified about changes.
   */
  private void notifyInterval() {
    if (game.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      notifyServer();
      return;
    }
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
    for (PlayerModel player : players) {
      userlist.getChildren().add(userLabelCreator(player.name));
      Slider slider = sliderCreator(player);
      userlist.getChildren().add(slider);
    }
  }

  private Label userLabelCreator(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    label.setStyle("-fx-font-size: 25px; -fx-background-color: #ffffff; -fx-text-fill: #000000;");
    return label;
  }

  private Slider sliderCreator(PlayerModel playerModel) {
    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(1);
    slider.setValue(0);
    String style = "-fx-background-image :url(\"%s\");";
    try {
      slider.setStyle(String.format(style, IconManager.iconForId(playerModel.iconId).getPath()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    userProgress.put(playerModel.userId, slider);
    return slider;
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
  public void receivedCheckeredFlag(long raceStop) {
    Platform.runLater(
        () -> {
          SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
          Date end = Timestamp.timestampToDate(raceStop);
          String stopTime = format.format(end);
          checkeredFlagLabel.setStyle("-fx-background-color: #000000;");
          checkeredFlagLabel.setDisable(false);
          checkeredFlagLabel.setText("Race Ending: " + stopTime);
        });
  }
}
