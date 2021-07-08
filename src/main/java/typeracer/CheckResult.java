package typeracer;

/** Used as result of check char. Contains all information about the check. */
public class CheckResult {

  private final CorrectionStates state;
  private final char expected;
  private final char typed;

  /**
   * Create Result.
   *
   * @param state whether the check was correct, incorrect, or got auto corrected
   * @param typed typed character
   * @param expected expected character
   */
  CheckResult(CorrectionStates state, char typed, char expected) {
    this.state = state;
    this.typed = typed;
    this.expected = expected;
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
}
