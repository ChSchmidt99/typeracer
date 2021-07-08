package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Unit tests for TextToType class. */
class TextToTypeTest {

  @Test
  void testCheckCharCorrect() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CheckResult actual = ttt.checkChar('t');
    CheckResult expectation = new CheckResult(CorrectionStates.CORRECT, 't', 't');
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckCharAutocorrected() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CheckResult actual = ttt.checkChar('z');
    CheckResult expectation = new CheckResult(CorrectionStates.AUTOCORRECTED, 'z', 't');
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckCharIncorrect() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CheckResult actual = ttt.checkChar('x');
    CheckResult expectation = new CheckResult(CorrectionStates.INCORRECT, 'x', 't');
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckFinishFalse() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    ttt.checkChar('t');
    boolean actual = ttt.checkFinish();
    assertFalse(actual);
  }

  @Test
  void testCheckFinishTrue() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("t", counter);
    ttt.checkChar('t');
    boolean actual = ttt.checkFinish();
    assertTrue(actual);
  }
}
