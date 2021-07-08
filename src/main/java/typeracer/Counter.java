package typeracer;

/** Tracks the index of the text to type. */
public class Counter {

  private int value;

  public Counter() {
    value = 0;
  }

  /**
   * Decrease the countdown value by one.
   *
   * @return the current countdown value after decreasing.
   */
  int decrease() { // user backspace
    assert value > 0 : "The countdown value can't be decreased if it already is " + value;
    value--;
    assert value >= 0 : "The countdown value can never be less than 0 (is " + value + ")";
    return value;
  }

  int increase() { // every key press
    value++;
    return value;
  }

  /** Set the countdown to zero. */
  void setToZero() {
    assert value > 0 : "Countdown value can only be set to zero if it is positive. It is " + value;
    value = 0;
  }

  public int getCurrentValue() {
    return value;
  }
}
