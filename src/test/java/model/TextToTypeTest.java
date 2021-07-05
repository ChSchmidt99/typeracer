package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for TextToType class. */
class TextToTypeTest {

  @Test
  void testCheckCharCorrect() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CorrectionStates actual = ttt.checkChar('t');
    CorrectionStates expectation = CorrectionStates.CORRECT;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckCharAutocorrected() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CorrectionStates actual = ttt.checkChar('z');
    CorrectionStates expectation = CorrectionStates.AUTOCORRECTED;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckCharIncorrect() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    CorrectionStates actual = ttt.checkChar('x');
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(actual, expectation);
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
