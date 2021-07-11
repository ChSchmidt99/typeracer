package app.screens.createSingleplayer;

import app.IconManager;
import app.screens.singleplayer.SingleplayerController;
import app.screens.singleplayer.SingleplayerModel;
import app.screens.singleplayer.SingleplayerView;
import app.screens.start.StartScreenController;
import app.screens.start.StartScreenModel;
import app.screens.start.StartScreenView;
import database.TextDatabase;
import java.io.FileNotFoundException;
import java.io.IOException;
import protocol.RaceDataSingleplayer;

public class CreateSingleplayerController {

  private final CreateSingleplayerModel model;
  private final CreateSingleplayerView view;

  /**
   * Creates new controller and shows view for singleplayer.
   *
   * @param model to fill view with
   * @param view that will be shown
   */
  public CreateSingleplayerController(CreateSingleplayerModel model, CreateSingleplayerView view)
      throws FileNotFoundException {
    this.view = view;
    this.model = model;
    bindButtons(view);
    view.show();
  }

  private void bindButtons(CreateSingleplayerView view) throws FileNotFoundException {
    view.getStartButton().setOnAction(actionEvent -> {
      try {
        clickedStart();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    view.getBackButton().setOnAction(actionEvent -> clickedBack());
    view.getUsernameLabel().setText("User: " + model.name);
    view.getUsericon().setImage(IconManager.iconForId(model.iconId).getImage());
  }

  private void clickedBack() {
    StartScreenView view = new StartScreenView(this.view.getStage());
    new StartScreenController(new StartScreenModel(), view);
  }

  private void clickedStart() throws IOException {
    new SingleplayerController(new SingleplayerModel(new RaceDataSingleplayer(new TextDatabase().getPhrase(), 0, model.name,
        model.iconId) ), new SingleplayerView(view.getStage()));
  }
}
