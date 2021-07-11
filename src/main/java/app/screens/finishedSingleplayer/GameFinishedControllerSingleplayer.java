package app.screens.finishedSingleplayer;

import app.screens.start.StartScreenController;
import app.screens.start.StartScreenModel;
import app.screens.start.StartScreenView;
import java.io.FileNotFoundException;

/** Controller for game finished screen. */
public class GameFinishedControllerSingleplayer implements GameFinishedModelObserverSingleplayer {

  private final GameFinishedModelSingleplayer model;
  private final GameFinishedViewSingleplayer view;

  /**
   * Creates new controller and shows view.
   *
   * @param model to fill view with
   * @param view that will be shown
   */
  public GameFinishedControllerSingleplayer(
      GameFinishedModelSingleplayer model, GameFinishedViewSingleplayer view) {
    this.view = view;
    this.model = model;
    bindings(view);
    try {
      this.view.updateView(model.getUsername(), model.getIconId(), model.getDuration());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.view.show();
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindings(GameFinishedViewSingleplayer view) {
    view.getReturnButton()
        .setOnAction(
            event -> {
              try {
                clickedReturn();
              } catch (FileNotFoundException e) {
                e.printStackTrace();
              }
            });
  }

  private void clickedReturn() throws FileNotFoundException {
    new StartScreenController(new StartScreenModel(), new StartScreenView(view.getStage()));
  }
}
