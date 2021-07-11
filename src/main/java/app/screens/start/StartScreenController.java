package app.screens.start;

import app.IconManager;
import app.screens.browser.LobbyBrowserController;
import app.screens.browser.LobbyBrowserModel;
import app.screens.browser.LobbyBrowserView;
import app.screens.createsingleplayer.CreateSingleplayerController;
import app.screens.createsingleplayer.CreateSingleplayerModel;
import app.screens.createsingleplayer.CreateSingleplayerView;
import java.io.IOException;

/** Handles transition functionality for startscreen. */
public class StartScreenController implements StartScreenModelObserver {

  private static final String USERNAME_ERROR = "Please choose a username";

  private final StartScreenModel model;
  private final StartScreenView view;

  /**
   * Creates new Start screen and shows it.
   *
   * @param model {@link StartScreenModel}
   * @param view {@link StartScreenView}
   */
  public StartScreenController(StartScreenModel model, StartScreenView view) {
    this.view = view;
    this.model = model;
    bindButtons(view);
    model.setObserver(this);
    this.view.show();
  }

  /** Called when user was registered successfully. */
  public void registered() {
    new LobbyBrowserController(new LobbyBrowserModel(), new LobbyBrowserView(view.getStage()));
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindButtons(StartScreenView view) {
    view.getSingleplayerButton()
        .setOnAction(
            (actionEvent) -> {
              try {
                clickedSingleplayer();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
    view.getMultiplayerButton().setOnAction((actionEvent) -> clickedMultiplayer());
  }

  private void clickedMultiplayer() {
    if (view.getUsername().equals("")) {
      view.displayError(USERNAME_ERROR);
    } else {
      try {
        model.register(view.getUsername());
      } catch (IOException e) {
        view.displayError(e.getMessage());
      }
    }
  }

  private void clickedSingleplayer() throws IOException {
    if (view.getUsername().equals("")) {
      view.displayError(USERNAME_ERROR);
    } else {
      CreateSingleplayerModel model =
          new CreateSingleplayerModel(view.getUsername(), IconManager.getSelectedIcon().getId());
      CreateSingleplayerView view = new CreateSingleplayerView(this.view.getStage());
      new CreateSingleplayerController(model, view);
    }
  }
}
