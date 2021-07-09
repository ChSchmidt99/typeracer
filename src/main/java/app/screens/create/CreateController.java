package app.screens.create;

import app.screens.browser.LobbyBrowserController;
import app.screens.browser.LobbyBrowserModel;
import app.screens.browser.LobbyBrowserView;
import app.screens.lobby.GameLobbyController;
import app.screens.lobby.GameLobbyModel;
import app.screens.lobby.GameLobbyView;
import protocol.LobbyData;

/** Handles all gui functionality for game creation. */
public class CreateController implements CreateModelObserver {

  private static final String LOBBY_NAME_ERROR = "Please enter lobby name";

  private final CreateModel model;
  private final CreateView view;

  /**
   * Creates new controller and shows view.
   *
   * @param model to fill view with
   * @param view that will be shown
   */
  public CreateController(CreateModel model, CreateView view) {
    this.view = view;
    bindButtons(view);
    this.model = model;
    model.setObserver(this);
    view.show();
  }

  @Override
  public void joinedLobby(LobbyData lobby) {
    new GameLobbyController(new GameLobbyModel(lobby), new GameLobbyView(view.getStage()));
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindButtons(CreateView view) {
    view.getCreateButton().setOnAction(actionEvent -> clickedCreate());
    view.getBackButton().setOnAction(actionEvent -> clickedBack());
  }

  private void clickedBack() {
    LobbyBrowserView view = new LobbyBrowserView(this.view.getStage());
    new LobbyBrowserController(new LobbyBrowserModel(), view);
  }

  private void clickedCreate() {
    if (view.getLobbyName().equals("")) {
      view.displayError(LOBBY_NAME_ERROR);
    } else {
      model.createLobby(view.getLobbyName());
    }
  }
}
