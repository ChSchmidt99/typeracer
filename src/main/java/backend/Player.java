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
  private int progress;
  private int mistakes;

  Player(String userId, String connectionId, String name) {
    this.userId = userId;
    this.connectionId = connectionId;
    this.name = name;
  }

  String getConnectionId() {
    return connectionId;
  }

  String getName() {
    return name;
  }

  void updateProgress(ProgressSnapshot snapshot) {
    progress = snapshot.progress;
    mistakes = snapshot.mistakes;
  }

  PlayerUpdate getUpdate() {
    // TODO: Implement me
    int wpm = 0;
    float progress = 0;
    return new PlayerUpdate(this.userId, wpm, progress);
  }

}
