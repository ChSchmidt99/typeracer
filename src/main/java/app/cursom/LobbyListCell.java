package app.cursom;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import protocol.LobbyData;

/** Fills Cells of the Lobby List in our lobby browser. */
public class LobbyListCell extends ListCell<LobbyData> {

  private static final String FXML_PATH = "view/lobbylistcell.fxml";

  @FXML Label lobbyNameLabel;

  @FXML Label idLabel;

  @FXML Label statusLabel;

  @FXML Button joinButton;

  private FXMLLoader loader;

  private String lobbyId;

  private final JoinHandler handler;

  /**
   * Called for all Cells in Lobby list.
   *
   * @param handler will be called on join button click
   */
  public LobbyListCell(JoinHandler handler) {
    this.handler = handler;
    try {
      loadFxml();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadFxml() throws IOException {
    if (loader == null) {
      loader =
          new FXMLLoader(
              (Objects.requireNonNull(
                  getClass().getProtectionDomain().getClassLoader().getResource(FXML_PATH))));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    }
  }

  @Override
  public void updateItem(LobbyData model, boolean empty) {
    if (empty || model == null) {
      clearContent();
    } else {
      lobbyId = model.id;
      setContent(model);
    }
  }

  @FXML
  private void clickedJoin() {
    handler.clickedJoin(lobbyId);
  }

  private void clearContent() {
    joinButton.setVisible(false);
    lobbyNameLabel.setVisible(false);
    idLabel.setVisible(false);
    statusLabel.setVisible(false);
    setText(null);
  }

  private void setContent(LobbyData model) {
    joinButton.setVisible(true);
    lobbyNameLabel.setText(model.name);
    lobbyNameLabel.setVisible(true);
    statusLabel.setText("Players: " + model.players.size());
    statusLabel.setVisible(true);
    if (model.isRunning) {
      idLabel.setText("In Race");
    } else {
      idLabel.setText("In Lobby");
    }
    idLabel.setVisible(true);
  }
}
