package app.screens.singleplayer;

import app.screens.finished.GameFinishedController;
import app.screens.finished.GameFinishedModel;
import app.screens.finished.GameFinishedView;
import app.screens.finishedSingleplayer.GameFinishedControllerSingleplayer;
import app.screens.finishedSingleplayer.GameFinishedModelSingleplayer;
import app.screens.finishedSingleplayer.GameFinishedViewSingleplayer;
import app.screens.start.StartScreenController;
import app.screens.start.StartScreenModel;
import app.screens.start.StartScreenView;
import javafx.scene.input.KeyEvent;
import protocol.RaceResult;
import typeracer.CheckResult;

public class SingleplayerController implements SingleplayerModelObserver {

  private final SingleplayerModel model;
  private final SingleplayerView view;

  public SingleplayerController(SingleplayerModel model, SingleplayerView view) {
    this.view = view;
    this.model = model;
    bindings(view);
    model.setObserver(this);
    model.initRaceStart();
    view.setupView(model.getRaceData());
    view.show();
  }

  @Override
  public void raceStarted() {
    view.setCountdownLabelVisible(false);
  }

  @Override
  public void updatedRaceState() {
    view.updatedRaceState(model.getRaceUpdate());
  }

  @Override
  public void receivedRaceResult(RaceResult result) {
    new GameFinishedController(
        new GameFinishedModel(result), new GameFinishedView(view.getStage()));
    view.getStage().getScene().removeEventHandler(KeyEvent.KEY_TYPED, this::typedKey);
  }

  @Override
  public void changedFinishedMessage(FinishedMessage message) {
    setFinishedMessage(message);
  }

  @Override
  public void checkeredFlag(long raceEndTimestamp) {
    new GameFinishedControllerSingleplayer(
        new GameFinishedModelSingleplayer(model.username, model.iconId, model.getDuration()),
        new GameFinishedViewSingleplayer(view.getStage()));
  }

  @Override
  public void updatedTimer(long time) {
    view.updateTimer(time);
  }

  @Override
  public void updatedCountDown(long time) {
    view.updatedCountdownLabel(Long.toString(time));
  }

  @Override
  public void receivedError(String message) {
    view.displayError(message);
  }

  private void bindings(SingleplayerView view) {
    view.getLeaveButton().setOnAction(event -> clickedLeave());
    view.getStage().getScene().addEventHandler(KeyEvent.KEY_TYPED, this::typedKey);
  }

  private void clickedLeave() {
    new StartScreenController(new StartScreenModel(), new StartScreenView(view.getStage()));
    view.getStage().getScene().removeEventHandler(KeyEvent.KEY_TYPED, this::typedKey);
  }

  private void typedKey(KeyEvent event) {
    String typed = event.getCharacter();
    CheckResult check = model.typed(typed);
    if (check != null) {
      view.showTextProgress(check);
      model.updateProgress();
    }
  }

  private void setFinishedMessage(FinishedMessage message) {
    view.updatedCountdownLabel(message.getMainMessage());
    view.updatedCountdownSubtitle(message.getSubMessage());
  }
}
