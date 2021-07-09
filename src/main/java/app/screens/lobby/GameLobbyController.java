package app.screens.lobby;

import app.screens.browser.LobbyBrowserController;
import app.screens.browser.LobbyBrowserModel;
import app.screens.browser.LobbyBrowserView;
import app.screens.multiplayer.MultiplayerController;
import app.screens.multiplayer.MultiplayerModel;
import app.screens.multiplayer.MultiplayerView;
import java.util.List;
import protocol.ChatMessageData;
import protocol.RaceData;

/** Controller for game lobby. */
public class GameLobbyController implements GameLobbyModelObserver {

  private static final String CHECKBOX_ERROR = "Please check 'ready' box.";

  private final GameLobbyModel model;
  private final GameLobbyView view;

  /**
   * Creates new controller and shows view.
   *
   * @param model to fill view with
   * @param view that will be shown
   */
  public GameLobbyController(GameLobbyModel model, GameLobbyView view) {
    this.model = model;
    this.view = view;
    bindButtons(view);
    this.model.setObserver(this);
    model.setReady(view.getIsReady());
    this.model.requestHistory();
    view.updateLobby(model.getLobby());
    view.show();
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  @Override
  public void receivedChatHistory(List<ChatMessageData> chatHistory) {
    view.updateChat(chatHistory);
  }

  @Override
  public void updatedLobby() {
    view.updateLobby(model.getLobby());
  }

  @Override
  public void startedRace(RaceData race) {
    new MultiplayerController(new MultiplayerModel(race), new MultiplayerView(view.getStage()));
  }

  private void bindButtons(GameLobbyView view) {
    view.getStartButton().setOnAction(event -> clickedStart());
    view.getBackButton().setOnAction(event -> clickedBack());
    view.getLobbyCheckbox().setOnAction(event -> checkedReady());
    view.getSendButton().setOnAction(event -> clickedSendMessage());
    view.getChatInputTextField().setOnAction(event -> clickedSendMessage());
  }

  private void clickedStart() {
    if (!view.getIsReady()) {
      view.displayError(CHECKBOX_ERROR);
      return;
    }
    model.startRace();
  }

  private void clickedBack() {
    model.leaveLobby();
    new LobbyBrowserController(new LobbyBrowserModel(), new LobbyBrowserView(this.view.getStage()));
  }

  private void checkedReady() {
    model.setReady(view.getIsReady());
  }

  private void clickedSendMessage() {
    if (view.getChatMessage().equals("")) {
      return;
    }
    model.sendMessage(view.getChatMessage());
    view.getChatInputTextField().setText("");
  }
}
