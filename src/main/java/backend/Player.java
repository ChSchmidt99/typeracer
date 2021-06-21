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
  //private int mistakes;

  Player(String userId, String connectionId, String name) {
    this.userId = userId;
    this.connectionId = connectionId;
    this.name = name;
    this.wpm = 0;
    this.progress = 0;
    //this.mistakes = 0;
  }

  String getConnectionId() {
    return connectionId;
  }

  String getName() {
    return name;
  }

  void updateProgress(ProgressSnapshot snapshot, int textLength) {
    long durationSec = snapshot.timestamp - snapshot.raceStartTime;
    this.wpm = wordsPerMinute(snapshot.progress, durationSec);
    this.progress = (float) snapshot.progress / textLength;
    //this.mistakes = snapshot.mistakes;
  }

  PlayerUpdate getUpdate() {
    return new PlayerUpdate(this.userId, wpm, progress);
  }

  private int wordsPerMinute(int charsTyped, long durationInSec) {
    float durationInMin = (float) durationInSec / 60;
    int wordsTyped = charsTyped / 5;
    System.out.println(wordsTyped + " / " + durationInMin);
    return (int) (wordsTyped / durationInMin);
  }

}
