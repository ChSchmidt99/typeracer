package backend;

import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;

/**
 * Used to represent a Player in an ongoing race.
 */
public class Player {

  private final String userId;
  private final String name;
  private final String connectionId;
  private int wpm;
  private float progress;
  private long raceStartTime;
  private long lastUpdateTime;
  //private int mistakes;

  Player(String userId, String connectionId, String name) {
    this.userId = userId;
    this.connectionId = connectionId;
    this.name = name;
    this.wpm = 0;
    this.progress = 0;
    this.raceStartTime = 0;
    this.lastUpdateTime = 0;
    //this.mistakes = 0;
  }

  String getConnectionId() {
    return connectionId;
  }

  String getName() {
    return name;
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
    //this.mistakes = snapshot.mistakes;
  }

  PlayerUpdate getUpdate() {
    return new PlayerUpdate(this.userId, wpm, progress, isFinished(), this.raceDuration());
  }

  private long raceDuration() {
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
