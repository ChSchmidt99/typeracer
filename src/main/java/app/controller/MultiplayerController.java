package app.controller;

import app.IconManager;
import app.elements.RaceTrack;
import app.model.GameFinishedModel;
import app.model.MultiplayerModel;
import app.model.MultiplayerModelObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import protocol.PlayerData;
import protocol.PlayerUpdate;
import util.Timestamp;

/** Handles all gui functionality associated with gameplay. */
class MultiplayerController extends Controller implements MultiplayerModelObserver {

  private static final String FXMLPATH = "view/multiplayer.fxml";

  // TODO: Combine in auxiliary class
  HashMap<String, Label> wpmLabels = new HashMap<>();
  HashMap<String, RaceTrack> userProgress = new HashMap<>();
  private final MultiplayerModel model;
  private int colorAlternateCounter = 0;

  @FXML TextFlow textToType;

  @FXML TextFlow enteredText;

  @FXML VBox userList;

  @FXML Label checkeredFlagLabel;

  /**
   * Controller for Multiplayer game screen.
   *
   * @param stage where controller is hosted in
   */
  public MultiplayerController(Stage stage, MultiplayerModel model) throws IOException {
    super(stage, FXMLPATH);
    this.model = model;
    model.setObserver(this);
    setupText(model.getRaceData().textToType);
    setupKeyHandler();
    setupTracks(model.getRaceData().players);
  }

  @Override
  public void updatedRaceState() {
    List<PlayerUpdate> updates = model.getRaceUpdate();
    for (PlayerUpdate update : updates) {
      trackUpdate(update);
      wpmUpdate(update);
    }
  }

  @Override
  public void checkeredFlag(long raceEndTimestamp) {
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Date end = Timestamp.timestampToDate(raceEndTimestamp);
    String stopTime = format.format(end);
    checkeredFlagLabel.setStyle("-fx-background-color: #000000;");
    checkeredFlagLabel.setDisable(false);
    checkeredFlagLabel.setText("Race Ending: " + stopTime);
    openGameOverScreen();
  }

  private void openGameOverScreen() {
    model.leaveRace();
    try {
      new GameFinishedController(stage, new GameFinishedModel()).show();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
              String typed = event.getCharacter();
              CheckResult check = model.typed(typed);
              if (check != null) {
                enteredText.getChildren().add(charLabelCreator(check));
              }
            });
  }

  /*
   * Adds the user list along with progress bars and wpm to game screen.
   */
  private void setupTracks(List<PlayerData> players) {
    for (PlayerData player : players) {
      HBox userHbox = new HBox();
      userHbox.setSpacing(20);
      wpmCreator(player.userId);
      wpmLabels.get(player.userId).setStyle("-fx-font-size: 20px; -fx-text-fill: #62fbf7;");
      userHbox.getChildren().add(userLabelCreator(player.name));
      userHbox.getChildren().add(wpmLabels.get(player.userId));
      userList.getChildren().add(userHbox);
      RaceTrack track = trackCreator(player);
      wpmLabels
          .get(player.userId)
          .setStyle("-fx-font-size: 20px; -fx-text-fill: #62fbf7; -fx-min-width: 40px;");

      VBox userVbox = new VBox();
      userVbox.getChildren().add(userLabelCreator(player.name));
      userVbox.getChildren().add(wpmLabels.get(player.userId));

      userHbox.getChildren().add(userVbox);
      userHbox.getChildren().add(track);

      userList.getChildren().add(userHbox);
      userProgress.put(player.userId, track);
    }
  }

  private void setupText(String t) {
    Text text = new Text(t);
    text.setFill(Color.web("#62fbf7"));
    textToType.getChildren().addAll(text);
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

  private Label userLabelCreator(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    label.setStyle(
        "-fx-font-size: 25px; -fx-background-color: #ffffff; "
            + "-fx-text-fill: #000000; -fx-min-width: 150px;");
    return label;
  }

  private RaceTrack trackCreator(PlayerData playerData) {
    try {
      colorAlternateCounter++;
      if (colorAlternateCounter % 2 == 0) {
        return new RaceTrack(
            IconManager.iconForId(playerData.iconId), 450, 25, Color.web("#fe55f7"));
      } else {
        return new RaceTrack(
            IconManager.iconForId(playerData.iconId), 450, 25, Color.web("#62fbf7"));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void wpmCreator(String userId) {
    Label label = new Label();
    label.setText("WPM: 0");
    label.setStyle("-fx-text-fill:#ffffff;");
    wpmLabels.put(userId, label);
  }

  private void wpmUpdate(PlayerUpdate update) {
    wpmLabels.get(update.userId).setText("WPM: " + update.wpm);
  }

  private void trackUpdate(PlayerUpdate update) {
    userProgress.get(update.userId).updateProgress(update.percentProgress);
  }
}
