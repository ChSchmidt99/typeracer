package app.screens.lobby;

import app.screens.View;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import protocol.ChatMessageData;
import protocol.LobbyData;
import protocol.UserData;

/** View for lobby screen. */
public class GameLobbyView extends View {

  private static final String FXML_PATH = "view/gamelobby.fxml";

  @FXML private CheckBox lobbyCheckbox;

  @FXML private Button startButton;

  @FXML private Button backButton;

  @FXML private Button sendButton;

  @FXML private ListView<String> userlist;

  @FXML private ListView<String> chatListView;

  @FXML private TextField chatInputTextField;

  /**
   * Create new lobby view.
   *
   * @param stage to host view
   */
  public GameLobbyView(Stage stage) {
    super(stage, FXML_PATH);
  }

  Button getStartButton() {
    return startButton;
  }

  Button getBackButton() {
    return backButton;
  }

  Button getSendButton() {
    return sendButton;
  }

  CheckBox getLobbyCheckbox() {
    return lobbyCheckbox;
  }

  TextField getChatInputTextField() {
    return chatInputTextField;
  }

  String getChatMessage() {
    return chatInputTextField.getText();
  }

  boolean getIsReady() {
    return lobbyCheckbox.isSelected();
  }

  void updateLobby(LobbyData lobby) {
    userlist.getItems().clear();
    List<String> items = new ArrayList<>();
    for (UserData user : lobby.players) {
      items.add(toListItem(user));
    }
    userlist.getItems().addAll(items);
    this.startButton.setDisable(lobby.isRunning);
  }

  void updateChat(List<ChatMessageData> chatHistory) {
    chatListView.getItems().clear();
    for (ChatMessageData message : chatHistory) {
      chatListView.getItems().add(message.user.name + ": " + message.message);
    }
  }

  private String toListItem(UserData userData) {
    String skeleton = "%s (%s)";
    return String.format(skeleton, userData.name, userData.state);
  }
}
