package app.screens.singleplayer;

import app.IconManager;
import app.custom.RaceTrack;
import app.screens.View;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import protocol.PlayerUpdate;
import protocol.RaceDataSingleplayer;
import typeracer.CheckResult;

/** View for Singleplayer screen. */
public class SingleplayerView extends View {

  private static final String FXML_PATH = "view/singleplayer.fxml";

  private final HashMap<String, app.screens.singleplayer.Lane> lanes;

  private final List<Label> textLabels;

  @FXML private TextFlow textFlow;

  @FXML private VBox userList;

  @FXML private Label timerLabel;

  @FXML private Label countdownLabel;

  @FXML private Label countdownSubtitle;

  @FXML private Button leaveButton;

  /**
   * Create new multiplayer view.
   *
   * @param stage to host view
   */
  public SingleplayerView(Stage stage) {
    super(stage, FXML_PATH);
    this.lanes = new HashMap<>();
    textLabels = new ArrayList<>();
  }

  public void updateTimer(long time) {
    Platform.runLater(() -> timerLabel.setText("Time: " + time + "s"));
  }

  public void setupView(RaceDataSingleplayer data) {
    setupText(data.textToType);
    setupTracks(data.name, data.iconId);
  }

  Button getLeaveButton() {
    return leaveButton;
  }

  void updatedCountdownLabel(String text) {
    countdownLabel.setText(text);
  }

  void setCountdownLabelVisible(boolean isVisible) {
    countdownLabel.setVisible(isVisible);
  }

  void updatedCountdownSubtitle(String text) {
    countdownSubtitle.setText(text);
  }

  void updatedRaceState(PlayerUpdate update) {
    trackUpdate(update);
    wpmUpdate(update);
  }

  /** Creates labels for user input which will be added to hbox enteredText. */
  void showTextProgress(CheckResult check) {
    switch (check.getState()) {
      case CORRECT:
        textLabels.get(check.getTextIndex()).setStyle("-fx-text-fill: #62fbf7;");
        break;
      case INCORRECT:
        textLabels.get(check.getTextIndex()).setStyle("-fx-text-fill: #fe55f7;");
        break;
      case AUTOCORRECTED:
        textLabels.get(check.getTextIndex()).setStyle("-fx-text-fill: #d789f7;");
        break;
      default:
    }
  }

  /*
   * Adds the user list along with progress bars and wpm to game screen.
   */
  private void setupTracks(String name, String iconId) {
    VBox userVbox = new VBox();
    Label wpmLabel = wpmCreator();
    userVbox.getChildren().add(userLabelCreator(name));
    userVbox.getChildren().add(wpmLabel);
    wpmLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #62fbf7; -fx-min-width: 40px;");

    HBox userHbox = new HBox();

    RaceTrack track = trackCreator(iconId, Color.web("#62fbf7"));

    userHbox.getChildren().add(userVbox);
    userHbox.getChildren().add(track);

    userList.getChildren().add(userHbox);
    lanes.put(name, new app.screens.singleplayer.Lane(wpmLabel, track));
  }

  private void setupText(String t) {
    for (int i = 0; i < t.length(); i++) {
      Label character = new Label(Character.toString(t.charAt(i)));
      character.setStyle("-fx-text-fill: #ffffff");
      textLabels.add(character);
      textFlow.getChildren().addAll(character);
    }
  }

  private Label userLabelCreator(String user) {
    Label label = new Label(user);
    label.setTextFill(Color.WHITE);
    label.setStyle(
        "-fx-font-size: 20px; -fx-background-color: #ffffff; "
            + "-fx-text-fill: #000000; -fx-min-width: 150px;");
    return label;
  }

  private RaceTrack trackCreator(String iconId, Paint color) {
    try {
      return new RaceTrack(IconManager.iconForId(iconId), 550, 20, color);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Label wpmCreator() {
    Label label = new Label();
    label.setText("WPM: 0");
    label.setStyle("-fx-text-fill:#ffffff;");
    return label;
  }

  private void wpmUpdate(PlayerUpdate update) {
    lanes.get(update.userId).getWordsPerMinuteLabel().setText("WPM: " + update.wpm);
  }

  private void trackUpdate(PlayerUpdate update) {
    lanes.get(update.userId).getTrack().updateProgress(update.percentProgress);
  }
}
