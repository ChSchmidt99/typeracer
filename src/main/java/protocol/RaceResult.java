package protocol;

import java.util.List;

public class RaceResult {

  /**
   * Duration of race.
   */
  public final long duration;

  /**
   * List of players ordered from best to worst.
   */
  public List<UserResult> classification;

  public RaceResult(long duration, List<UserResult> classification) {
    this.duration = duration;
    this.classification = classification;
  }
}
