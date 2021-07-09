package app.screens.browser;

import app.screens.create.CreateController;
import app.screens.create.CreateModel;
import app.screens.create.CreateView;
import app.screens.lobby.GameLobbyController;
import app.screens.lobby.GameLobbyModel;
import app.screens.lobby.GameLobbyView;
import app.screens.start.StartScreenController;
import app.screens.start.StartScreenModel;
import app.screens.start.StartScreenView;
import java.util.List;
import protocol.LobbyData;

/** Controller for LobbyBrowser. */
public class LobbyBrowserController implements LobbyBrowserObserver {

  private final LobbyBrowserModel model;
  private final LobbyBrowserView view;

  /**
   * Creates LobbyBrowser and shows it.
   *
   * @param model {@link LobbyBrowserModel}
   * @param view {@link LobbyBrowserView}
   */
  public LobbyBrowserController(LobbyBrowserModel model, LobbyBrowserView view) {
    this.model = model;
    this.view = view;
    bindButtons(view);
    model.setObserver(this);
    view.show();
    model.createdView();
  }

  @Override
  public void receivedOpenLobbies(List<LobbyData> lobbies) {
    this.view.setLobbyList(lobbies);
  }

  @Override
  public void joinedLobby(LobbyData lobby) {
    System.out.println("Should join");
    new GameLobbyController(new GameLobbyModel(lobby), new GameLobbyView(view.getStage()));
    model.leftScreen();
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindButtons(LobbyBrowserView view) {
    view.getBackButton().setOnAction(event -> clickedBack());
    view.getCreateButton().setOnAction(event -> clickedCreate());
    view.setOnJoin(this::clickedJoin);
  }

  private void clickedBack() {
    model.leftScreen();
    StartScreenView view = new StartScreenView(this.view.getStage());
    StartScreenModel model = new StartScreenModel();
    new StartScreenController(model, view);
  }

  private void clickedCreate() {
    new CreateController(new CreateModel(), new CreateView(view.getStage()));
    model.leftScreen();
  }

  private void clickedJoin(String lobbyId) {
    model.joinLobby(lobbyId);
  }
}
