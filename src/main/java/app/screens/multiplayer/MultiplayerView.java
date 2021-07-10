package app.screens.multiplayer;

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
import protocol.RaceData;
import protocol.UserData;
import typeracer.CheckResult;

/** View for multiplayer. */
public class MultiplayerView extends View {

  private static final String FXML_PATH = "view/multiplayer.fxml";

  private final HashMap<String, Lane> lanes;
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
  public MultiplayerView(Stage stage) {
    super(stage, FXML_PATH);
    lanes = new HashMap<>();
    textLabels = new ArrayList<>();
  }

  public void updateTimer(long time) {
    Platform.runLater(() -> timerLabel.setText("Time: " + time + "s"));
  }

  public void setupView(RaceData data) {
    setupText(data.textToType);
    setupTracks(data.players);
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

  void setCountdownSubtitleVisible(boolean isVisible) {
    countdownSubtitle.setVisible(isVisible);
  }

  void updatedRaceState(List<PlayerUpdate> updates) {
    for (PlayerUpdate update : updates) {
      trackUpdate(update);
      wpmUpdate(update);
    }
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
  private void setupTracks(List<UserData> players) {
    for (int i = 0; i < players.size(); i++) {
      UserData player = players.get(i);
      VBox userVbox = new VBox();
      Label wpmLabel = wpmCreator();
      userVbox.getChildren().add(userLabelCreator(player.name));
      userVbox.getChildren().add(wpmLabel);
      wpmLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #62fbf7; -fx-min-width: 40px;");

      HBox userHbox = new HBox();
      userHbox.setSpacing(20);

      Paint color;
      if (i % 2 == 0) {
        color = Color.web("#62fbf7");
      } else {
        color = Color.web("#fe55f7");
      }
      RaceTrack track = trackCreator(player, color);

      userHbox.getChildren().add(userVbox);
      userHbox.getChildren().add(track);

      userList.getChildren().add(userHbox);
      lanes.put(player.userId, new Lane(wpmLabel, track));
    }
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

  private RaceTrack trackCreator(UserData userData, Paint color) {
    try {
      return new RaceTrack(IconManager.iconForId(userData.iconId), 550, 20, color);
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
