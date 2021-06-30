package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for TextToType class. */
class TextToTypeTest {

  @Test
  void testCheckChar() {
    boolean expectation = true;
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    boolean actual = ttt.checkChar('t');
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckFinishFalse() {
    boolean expectation = false;
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    ttt.checkChar('t');
    boolean actual = ttt.checkFinish();
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckFinishTrue() {
    boolean expectation = true;
    Counter counter = new Counter();
    TextToType ttt = new TextToType("t", counter);
    ttt.checkChar('t');
    boolean actual = ttt.checkFinish();
    assertEquals(expectation, actual);
  }
}
