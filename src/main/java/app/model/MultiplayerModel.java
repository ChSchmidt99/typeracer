package app.model;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.RaceObserver;
import client.RaceResultObserver;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.RaceData;
import protocol.RaceResult;
import typeracer.CheckResult;
import typeracer.GamePhase;
import typeracer.Typeracer;
import util.Timestamp;

/** Model for Multiplayer View. */
public class MultiplayerModel implements RaceObserver, RaceResultObserver, ErrorObserver {

  private MultiplayerModelObserver observer;

  private final long raceStart;
  private final Typeracer typeracer;
  private final RaceData raceData;
  private List<PlayerUpdate> updates;
  private int notifyCounter;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  /**
   * Create Model for Multiplayer View.
   *
   * @param race information about started race
   */
  public MultiplayerModel(RaceData race) {
    this.raceData = race;
    this.raceStart = Timestamp.currentTimestamp();
    this.typeracer = new Typeracer(race.textToType);
    this.notifyCounter = 0;
    subscribe();
    timer();
  }

  /**
   * Set Observer.
   *
   * @param observer observer
   */
  public void setObserver(MultiplayerModelObserver observer) {
    this.observer = observer;
  }

  /**
   * Get information about the race.
   *
   * @return {@link RaceData}
   */
  public RaceData getRaceData() {
    return raceData;
  }

  /**
   * Call when a key was typed.
   *
   * @param key typed key
   * @return {@link CheckResult}
   */
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
    unsubscribe();
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

  public void timer() {
    final Runnable timer =
        new Runnable() {
          public void run() {
            long time = Timestamp.currentTimestamp() - raceStart;
            if (observer != null) {
              observer.updatedTimer(time);
            }
          }
        };
    final ScheduledFuture<?> timerHandle =
        scheduler.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
    scheduler.schedule(
        new Runnable() {
          public void run() {
            timerHandle.cancel(true);
          }
        },
        60 * 60,
        TimeUnit.SECONDS);
  }

  public void shutdownTimerScheduler() {
    this.scheduler.shutdownNow();
  }

  @Override
  public void receivedRaceResult(RaceResult result) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedRaceResult(result));
    }
  }

  @Override
  public void receivedError(String message) {
    if (observer != null) {
      Platform.runLater(() -> observer.receivedError(message));
    }
  }

  private void subscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.subscribeErrors(this);
    client.subscribeResults(this);
    client.subscribeRaceUpdates(this);
  }

  private void unsubscribe() {
    Client client = ApplicationState.getInstance().getClient();
    client.unsubscribeErrors(this);
    client.unsubscribeResults(this);
    client.unsubscribeRaceUpdates(this);
  }
}
