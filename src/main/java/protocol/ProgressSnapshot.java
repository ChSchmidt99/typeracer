package protocol;

/**
 * Used to create a JSON Snapshot of the current race progress.
 */
public class ProgressSnapshot {

  /**
   * Unix epoch time of the local race start.
   */
  public final long raceStartTime;

  /**
   * Unix epoch time of the update.
   */
  public final long timestamp;

  /**
   * How many characters of the text have been typed.
   */
  public final int progress;

  /**
   * Number of mistakes.
   */
  public final int mistakes;

  /**
   * Create a snapshot of the current progress.
   *
   * @param raceStart unix epoch time  of the race start
   * @param timestamp unix epoch time of the snapshot
   * @param progress number of typed characters
   * @param mistakes number of mistakes
   */
  public ProgressSnapshot(long raceStart, long timestamp, int progress, int mistakes) {
    this.raceStartTime = raceStart;
    this.timestamp = timestamp;
    this.progress = progress;
    this.mistakes = mistakes;
  }

}
