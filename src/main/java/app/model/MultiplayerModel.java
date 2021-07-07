package app.model;

import app.ApplicationState;
import client.Client;
import client.RaceObserver;
import java.util.List;
import javafx.application.Platform;
import model.CheckResult;
import model.GamePhase;
import model.Typeracer;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceModel;
import util.Timestamp;

public class MultiplayerModel implements RaceObserver {

  private MultiplayerModelObserver observer;

  private final long raceStart;
  private final Typeracer typeracer;
  private final RaceModel raceModel;
  private List<PlayerUpdate> updates;
  private int notifyCounter;

  public MultiplayerModel(RaceModel race) {
    this.raceModel = race;
    this.raceStart = Timestamp.currentTimestamp();
    this.typeracer = new Typeracer(race.textToType);
    this.notifyCounter = 0;
    ApplicationState.getInstance().getClient().subscribeRaceUpdates(this);
  }

  public void setObserver(MultiplayerModelObserver observer) {
    this.observer = observer;
  }

  public RaceModel getRaceModel() {
    return raceModel;
  }

  public CheckResult typed(String key) {
    if (typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      return null;
    }
    CheckResult check = typeracer.check(key.charAt(0));
    notifyInterval();
    return check;
  }

  public List<PlayerUpdate> getRaceUpdate() {
    return updates;
  }

  public void leaveRace() {
    ApplicationState.getInstance().getClient().unsubscribeRaceUpdates(this);
  }

  /*
   * Limits the interval, in which the server gets notified about changes.
   */
  private void notifyInterval() {
    if (typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      sendProgress();
      return;
    }
    if (notifyCounter++ == 2) {
      sendProgress();
      notifyCounter = 0;
    }
  }

  private void sendProgress() {
    Client client = ApplicationState.getInstance().getClient();
    client.sendProgressUpdate(
        new ProgressSnapshot(
            raceStart,
            Timestamp.currentTimestamp(),
            typeracer.getState().getTypeChar().getCounter(),
            typeracer.getState().getTypeChar().getMistakeCounter()));
  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {
    this.updates = updates;
    if (observer != null) {
      Platform.runLater(() -> observer.updatedRaceState());
    }
  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {
    if (observer != null) {
      Platform.runLater(() -> observer.checkeredFlag(raceStop));
    }
  }
}
