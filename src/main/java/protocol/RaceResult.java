package protocol;

import java.util.List;

/** Model for Race Result responses. */
public class RaceResult {

  /** Duration of race. */
  public final long duration;

  /** Phrase to type. */
  public final String text;

  /** List of players ordered from best to worst. */
  public final List<UserResult> classification;

  /**
   * Create new race result.
   *
   * @param duration duration of the race, time until first player finished
   * @param classification ordered list of all players according to finish position
   * @param textToType the text that was typed
   */
  public RaceResult(long duration, List<UserResult> classification, String textToType) {
    this.duration = duration;
    this.classification = classification;
    this.text = textToType;
  }
}
