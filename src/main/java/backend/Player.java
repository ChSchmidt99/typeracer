package backend;

import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.UserData;

/** Used to represent a Player in an ongoing race. */
public class Player {

  private final User user;
  private int wpm;
  private float progress;
  private long raceStartTime;
  private long lastUpdateTime;
  private int mistakes;

  Player(User user) {
    this.user = user;
    this.wpm = 0;
    this.progress = 0;
    this.raceStartTime = 0;
    this.lastUpdateTime = 0;
    this.mistakes = 0;
  }

  User getUser() {
    return user;
  }

  void updateProgress(ProgressSnapshot snapshot, int textLength) {
    if (isFinished()) {
      return;
    }
    // Only set raceStartTime once
    if (this.raceStartTime == 0) {
      this.raceStartTime = snapshot.raceStartTime;
    }
    this.lastUpdateTime = snapshot.timestamp;
    this.wpm = wordsPerMinute(snapshot.progress, this.raceDuration());
    this.progress = (float) snapshot.progress / textLength;
    this.mistakes = snapshot.mistakes;
  }

  PlayerUpdate getUpdate() {
    return new PlayerUpdate(this.user.getId(), wpm, progress, isFinished(), this.raceDuration());
  }

  int getWpm() {
    return wpm;
  }

  int getMistakes() {
    return this.mistakes;
  }

  UserData getUserData() {
    return user.getUserData();
  }

  long raceDuration() {
    return this.lastUpdateTime - raceStartTime;
  }

  private int wordsPerMinute(int charsTyped, long durationInSec) {
    float durationInMin = (float) durationInSec / 60;
    int wordsTyped = charsTyped / 5;
    return (int) (wordsTyped / durationInMin);
  }

  private boolean isFinished() {
    return this.progress == 1;
  }
}
