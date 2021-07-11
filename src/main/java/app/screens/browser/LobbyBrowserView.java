package app.screens.browser;

import app.custom.JoinHandler;
import app.custom.LobbyListCell;
import app.screens.View;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import protocol.LobbyData;

/** View for Lobby browser. */
public class LobbyBrowserView extends View implements JoinHandler {

  private static final String FXML_PATH = "view/openlobbies.fxml";

  private OnJoin onJoin;

  @FXML private ListView<LobbyData> lobbylist;

  @FXML private Button backButton;

  @FXML private Button createButton;

  /**
   * Create new lobby browser view.
   *
   * @param stage to host view
   */
  public LobbyBrowserView(Stage stage) {
    super(stage, FXML_PATH);
    initListView();
  }

  @Override
  public void clickedJoin(String lobbyId) {
    if (onJoin != null) {
      onJoin.calledJoin(lobbyId);
    }
  }

  Button getBackButton() {
    return backButton;
  }

  Button getCreateButton() {
    return createButton;
  }

  void setOnJoin(OnJoin join) {
    onJoin = join;
  }

  void setLobbyList(List<LobbyData> lobbies) {
    lobbylist.setItems(FXCollections.observableArrayList(lobbies));
  }

  void initListView() {
    lobbylist.setCellFactory(lobbyListView -> new LobbyListCell(this));
  }
}
