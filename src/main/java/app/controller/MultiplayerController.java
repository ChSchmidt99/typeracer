package app.controller;

import app.IconManager;
import app.elements.RaceTrack;
import client.Client;
import client.RaceObserver;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
  HashMap<String, Label> wpmLabels = new HashMap<>();
  String userId;
  HashMap<String, RaceTrack> userProgress = new HashMap<>();

  @FXML TextFlow textToType;

  @FXML TextFlow enteredText;

  @FXML VBox userlist;

  @FXML Label checkeredFlagLabel;

  public MultiplayerController(Stage stage, RaceModel race, Client client, String userId) {
    super(stage, FXMLPATH);
    this.client = client;
    this.userId = userId;
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
              try {
                String typed = event.getCharacter();
                CheckResult check = game.check(typed.charAt(0));
                enteredText.getChildren().add(charLabelCreator(check));
                notifyInterval();
              } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
              }
            });
  }

  /** Creates labels for user input which will be added to hbox enteredText. */
  private Label charLabelCreator(CheckResult check) {
    Label label = new Label();
    switch (check.state) {
      case CORRECT:
        Text characterCorrect = new Text(Character.toString(check.expected));
        characterCorrect.setFill(Color.WHITE);
        enteredText.getChildren().addAll(characterCorrect);
        break;
      case INCORRECT:
        Text characterIncorrect = new Text(Character.toString(check.typed));
        characterIncorrect.setFill(Color.web("#fe55f7"));
        enteredText.getChildren().addAll(characterIncorrect);
        break;
      case AUTOCORRECTED:
        Text characterAutocorrected = new Text(Character.toString(check.typed));
        characterAutocorrected.setFill(Color.web("#62fbf7"));
        enteredText.getChildren().addAll(characterAutocorrected);
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
    if (notifyCounter == 2) {
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
      HBox userHbox = new HBox();
      userHbox.setSpacing(20);
      wpmCreator(player.userId);
      wpmLabels.get(player.userId).setStyle("-fx-font-size: 20px; -fx-text-fill: #62fbf7;");
      userHbox.getChildren().add(userLabelCreator(player.name));
      userHbox.getChildren().add(wpmLabels.get(player.userId));
      userlist.getChildren().add(userHbox);
      Slider slider = sliderCreator(player);
      userlist.getChildren().add(slider);
      userlist.getChildren().add(userLabelCreator(player.name));
      RaceTrack track = trackCreator(player);
      userlist.getChildren().add(track);
      userProgress.put(player.userId, track);
    }
  }

  private Label userLabelCreator(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    label.setStyle("-fx-font-size: 25px; -fx-background-color: #ffffff; -fx-text-fill: #000000;");
    return label;
  }

  private RaceTrack trackCreator(PlayerModel playerModel) {
    try {
      return new RaceTrack(IconManager.iconForId(playerModel.iconId), 500, 50, Color.WHITE);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  private Slider sliderCreator(PlayerModel playerModel) {
    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(1);
    slider.setValue(0);
    userProgress.put(playerModel.userId, slider);
    return slider;
  }

  private void trackUpdate(PlayerUpdate update) {
    userProgress.get(update.userId).updateProgress(update.percentProgress);
  private void wpmCreator(String userId) {
    Label label = new Label();
    label.setText("Wpm: 0");
    label.setStyle("-fx-text-fill:#ffffff;");
    wpmLabels.put(userId, label);
  }

  private void sliderUpdate(PlayerUpdate update) {
    userProgress.get(update.userId).setValue(update.percentProgress);
  }

  private void wpmUpdate(PlayerUpdate update) {
    wpmLabels.get(update.userId).setText(String.valueOf(update.wpm));
  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {
    for (PlayerUpdate update : updates) {
      Platform.runLater(
          () -> {
            trackUpdate(update);
            sliderUpdate(update);
            wpmUpdate(update);
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
          new GameFinishedController(stage, client, userId);
        });
  }
}
