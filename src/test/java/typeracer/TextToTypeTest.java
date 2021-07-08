package typeracer;

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
    CheckResult result = ttt.checkChar('t');
    CorrectionStates expectation = CorrectionStates.CORRECT;
    assertEquals(result.state, expectation);
  }

  @Test
  void testCheckCharAutocorrected() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CheckResult result = ttt.checkChar('z');
    CorrectionStates expectation = CorrectionStates.AUTOCORRECTED;
    assertEquals(result.state, expectation);
  }

  @Test
  void testCheckCharIncorrect() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CheckResult result = ttt.checkChar('x');
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(result.state, expectation);
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
