package app.screens.finished;

import app.screens.lobby.GameLobbyController;
import app.screens.lobby.GameLobbyModel;
import app.screens.lobby.GameLobbyView;
import java.io.FileNotFoundException;
import protocol.LobbyData;

/** Controller for game finished screen. */
public class GameFinishedController implements GameFinishedModelObserver {

  private final GameFinishedModel model;
  private final GameFinishedView view;

  /**
   * Creates new controller and shows view.
   *
   * @param model to fill view with
   * @param view that will be shown
   */
  public GameFinishedController(GameFinishedModel model, GameFinishedView view) {
    this.view = view;
    this.model = model;
    this.model.setObserver(this);
    bindings(view);
    try {
      this.view.updateView(model.getRaceResult());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.view.show();
  }

  @Override
  public void receivedGameLobby(LobbyData lobby) {
    model.setObserver(null);
    new GameLobbyController(new GameLobbyModel(lobby), new GameLobbyView(view.getStage()));
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindings(GameFinishedView view) {
    view.getReturnButton().setOnAction(event -> clickedReturn());
  }

  private void clickedReturn() {
    model.requestLobby();
  }
}
