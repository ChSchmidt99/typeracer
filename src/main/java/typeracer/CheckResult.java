package typeracer;

/** Used as result of check char. Contains all information about the check. */
public class CheckResult {

  private final CorrectionStates state;
  private final char expected;
  private final char typed;
  private final int index;

  /**
   * Create Result.
   *
   * @param state whether the check was correct, incorrect, or got auto corrected
   * @param typed typed character
   * @param expected expected character
   */
  CheckResult(CorrectionStates state, char typed, char expected, int index) {
    this.state = state;
    this.typed = typed;
    this.expected = expected;
    this.index = index;
  }

  public char getExpected() {
    return expected;
  }

  public char getTyped() {
    return typed;
  }

  public CorrectionStates getState() {
    return state;
  }

  public int getTextIndex() {
    return index;
  }
}
