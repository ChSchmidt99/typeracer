package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** Unit tests for Counter class. */
class CounterTest {

  @Test
  void testIncreaseTwiceThenDecrease() {
    int expectation = 1;
    Counter counter = new Counter();
    counter.increase();
    counter.increase();
    counter.decrease();
    int actual = counter.getCurrentValue();
    assertEquals(expectation, actual);
  }

  @Test
  void testDecreaseFromZero() {
    try {
      Counter counter = new Counter();
      counter.decrease();
      fail("The countdown value can't be decreased if it already is 0");
    } catch (AssertionError e) {
      // no-op (pass)
    }
  }

  @Test
  void testIncrease() {
    int expectation = 1;
    Counter counter = new Counter();
    counter.increase();
    int actual = counter.getCurrentValue();
    assertEquals(expectation, actual);
  }

  @Test
  void testSetToZero() {
    int expectation = 0;
    Counter counter = new Counter();
    counter.increase();
    counter.increase();
    counter.setToZero();
    int actual = counter.getCurrentValue();
    assertEquals(expectation, actual);
  }
}
