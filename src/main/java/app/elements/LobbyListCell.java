package app.elements;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import protocol.LobbyModel;

import java.io.IOException;
import java.util.Objects;

/**
 * Fills Cells of the Lobby List in our lobby browser.
 */
public class LobbyListCell extends ListCell<LobbyModel> {

  @FXML
  Label lobbyNameLabel;

  @FXML
  Label idLabel;

  @FXML
  Label statusLabel;

  @FXML
  Button joinButton;

  private FXMLLoader loader;

  private String lobbyId;

  private final JoinHandler handler;

  public LobbyListCell(JoinHandler handler) {
    this.handler = handler;
    try {
      loadFXML();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadFXML() throws IOException {
    if (loader == null) {
      loader = new FXMLLoader((Objects.requireNonNull(getClass().getProtectionDomain()
              .getClassLoader().getResource("view/lobbylistcell.fxml"))));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    }
  }

  @Override
  public void updateItem(LobbyModel model, boolean empty) {
    if (empty) {
      clearContent();
    } else {
      lobbyId = model.id;
      setContent(model);
    }
  }

  @FXML
  private void clickedJoin(){
    handler.clickedJoin(lobbyId);
  }

  private void clearContent() {
    joinButton.setDisable(true);
    joinButton.setVisible(false);
    setText(null);
  }

  private void setContent(LobbyModel model) {
    joinButton.setDisable(model.isRunning);
    joinButton.setVisible(true);
    lobbyNameLabel.setText(model.name);
    statusLabel.setText("Players: " + model.players.size());
    idLabel.setText("ID: " + model.id);
  }
}
