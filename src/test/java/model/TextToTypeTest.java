package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



/** Unit tests for TextToType class. */
class TextToTypeTest {

  @Test
  void testCheckChar() {
    Counter counter = new Counter();
    TextToType ttt = new TextToType("test", counter);
    boolean actual = ttt.checkChar('t');
    assertTrue(actual);
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
