package app.screens.singleplayer;

import app.ApplicationState;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import protocol.PlayerUpdate;
import protocol.RaceDataSingleplayer;
import typeracer.CheckResult;
import typeracer.GamePhase;
import typeracer.Typeracer;
import util.Timestamp;

public class SingleplayerModel implements Closeable {

  /** Update interval in sec. */
  private static final long POLLING_INTERVAL = 1;

  private static final long FALL_BACK_START_DELAY = 3;
  private static final app.screens.singleplayer.FinishedMessage FINISHED =
      new app.screens.singleplayer.FinishedMessage("FINISHED", "waiting for race to end");
  private static final app.screens.singleplayer.FinishedMessage NOT_FINISHED =
      new app.screens.singleplayer.FinishedMessage("HURRY!", "");

  private SingleplayerModelObserver observer;

  private long raceStart;
  private final Typeracer typeracer;
  private final RaceDataSingleplayer raceData;
  private PlayerUpdate update;
  private State state;
  private final ScheduledExecutorService scheduler;
  private app.screens.singleplayer.FinishedMessage finishedMessage;
  private int wpm;

  String username;
  String iconId;

  enum State {
    PRE_START,
    RACING,
    CHECKERED_FLAG
  }

  public SingleplayerModel(RaceDataSingleplayer raceData) {
    this.username = raceData.name;
    this.iconId = raceData.iconId;
    this.scheduler = Executors.newScheduledThreadPool(2);
    this.raceData = raceData;
    this.typeracer = new Typeracer(raceData.textToType);
    this.state = State.PRE_START;
    this.finishedMessage = NOT_FINISHED;
    ApplicationState.getInstance().addCloseable(this);
  }

  @Override
  public void close() throws IOException {
    scheduler.shutdownNow();
  }

  void setObserver(SingleplayerModelObserver observer) {
    this.observer = observer;
  }

  RaceDataSingleplayer getRaceData() {
    return raceData;
  }

  CheckResult typed(String key) {
    if ((this.state == State.PRE_START)
        || typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      return null;
    }
    CheckResult check = typeracer.check(key.charAt(0));
    if (typeracer.getState().getCurrentGamePhase() == GamePhase.FINISHED) {
      updateView();
      if (observer != null) {
        Platform.runLater(
            () -> {
              observer.checkeredFlag(getDuration());
            });
      }
      this.state = State.CHECKERED_FLAG;
    }
    return check;
  }

  PlayerUpdate getRaceUpdate() {
    return update;
  }

  void initRaceStart() {
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

  private void updateView() {
    if (observer != null) {
      Platform.runLater(() -> observer.updatedRaceState());
    }
  }

  private void startPolling() {
    scheduler.scheduleAtFixedRate(this::tick, 0, POLLING_INTERVAL, TimeUnit.SECONDS);
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
        if (observer != null) {
          Platform.runLater(() -> observer.changedFinishedMessage(finishedMessage));
        }
        long time = Timestamp.currentTimestamp() - raceStart;
        if (observer != null) {
          Platform.runLater(() -> observer.updatedTimer(time));
        }
        updateView();
        break;
      default:
    }
  }

  void updateProgress() {
    long duration = Timestamp.currentTimestamp() - raceStart;
    int typedCharCounter = typeracer.getState().getTypeChar().getCounter();
    int wpm = wordsPerMinute(typedCharCounter, duration);
    this.wpm = wpm;
    int textLength = typeracer.getState().getTypeChar().getCompleteText().length();
    float progress = (float) typedCharCounter / textLength;
    this.update = new PlayerUpdate(username, wpm, progress, false, duration);
  }

  private int wordsPerMinute(int charsTyped, long durationInSec) {
    float durationInMin = (float) durationInSec / 60;
    int wordsTyped = charsTyped / 5;
    return (int) (wordsTyped / durationInMin);
  }

  long getDuration() {
    return Timestamp.currentTimestamp() - raceStart;
  }

  int getWpm() {
    return this.wpm;
  }

  double getAccuracy() {
    return (1.0
            - (double) typeracer.getState().getTypeChar().getMistakeCounter()
                / (double) typeracer.getState().getTypeChar().getCompleteText().length())
        * 100;
  }
}
