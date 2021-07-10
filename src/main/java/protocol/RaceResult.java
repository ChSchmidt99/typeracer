package protocol;

import java.util.List;

/** Model for Race Result responses. */
public class RaceResult {

  /** Duration of race. */
  public final long duration;

  /** Phrase to type. */
  public final String text;

  /** List of players ordered from best to worst. */
  public List<UserResult> classification;

  public RaceResult(long duration, List<UserResult> classification, String textToType) {
    this.duration = duration;
    this.classification = classification;
    this.text = textToType;
  }
}
