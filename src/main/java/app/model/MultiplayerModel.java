package app.model;

import app.ApplicationState;
import client.Client;
import client.ErrorObserver;
import client.RaceObserver;
import client.RaceResultObserver;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
public class MultiplayerModel
    implements RaceObserver, RaceResultObserver, ErrorObserver, Closeable {

  /** Update interval in sec. */
  private static final long POLLING_INTERVAL = 1;

  private static final long FALL_BACK_START_DELAY = 3;

  private MultiplayerModelObserver observer;

  private long raceStart;
  private long raceEnd;
  private final Typeracer typeracer;
  private final RaceData raceData;
  private List<PlayerUpdate> updates;
  private State state;
  private final ScheduledExecutorService scheduler;

  private enum State {
    PRE_START,
    RACING,
    CHECKERED_FLAG
  }

  /**
   * Create Model for Multiplayer View.
   *
   * @param race information about started race
   */
  public MultiplayerModel(RaceData race) {
    this.scheduler = Executors.newScheduledThreadPool(2);
    this.raceData = race;
    this.typeracer = new Typeracer(race.textToType);
    this.state = State.PRE_START;
    subscribe();
    ApplicationState.getInstance().addCloseable(this);
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
    if ((this.state == State.PRE_START)
        || typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      return null;
    }
    CheckResult check = typeracer.check(key.charAt(0));
    if (typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      sendProgress();
      this.state = State.CHECKERED_FLAG;
      this.raceEnd = Timestamp.currentTimestamp();
    }
    return check;
  }

  public List<PlayerUpdate> getRaceUpdate() {
    return updates;
  }

  /**
   * Call to leave the race.
   */
  public void leaveRace() {
    ApplicationState.getInstance().getClient().leaveLobby();
    leftScreen();
  }

  /** Call on screen exit. */
  public void leftScreen() {
    unsubscribe();
    try {
      close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    ApplicationState.getInstance().removeCloseable(this);
  }

  /** Call to initialize Race. */
  public void initRaceStart() {
    startPolling();
    if (raceData.startTime < Timestamp.currentTimestamp()) {
      // Server race start is already in the past, start race later;
      this.raceStart = Timestamp.currentTimestamp() + FALL_BACK_START_DELAY;
    } else {
      // Race start is in the future, schedule race start.
      this.raceStart = raceData.startTime;
    }
  }

  private void raceStart() {
    state = State.RACING;
    if (observer != null) {
      Platform.runLater(() -> observer.raceStarted());
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
    this.state = State.CHECKERED_FLAG;
    this.raceEnd = raceStop;
    if (observer != null) {
      Platform.runLater(
          () -> {
            observer.updatedCountDown(this.raceEnd - Timestamp.currentTimestamp());
            observer.checkeredFlag(raceStop);
          });
    }
  }

  public void startPolling() {
    scheduler.scheduleAtFixedRate(this::tick, 0, POLLING_INTERVAL, TimeUnit.SECONDS);
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

  private void tick() {
    switch (state) {
      case PRE_START:
        if (observer != null) {
          long countDown = this.raceStart - Timestamp.currentTimestamp();
          Platform.runLater(() -> observer.updatedCountDown(countDown));
          if (countDown == 0) {
            raceStart();
          }
        }
        break;
      case RACING:
        long time = Timestamp.currentTimestamp() - raceStart;
        if (observer != null) {
          Platform.runLater(() -> observer.updatedTimer(time));
        }
        sendProgress();
        break;
      default:
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

  @Override
  public void close() throws IOException {
    scheduler.shutdownNow();
  }
}
