package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;


/** Unit tests for Counter class. */
class CounterTest {

  @Test
  void testIncreaseTwiceThenDecrease() {
    Counter counter = new Counter();
    counter.increase();
    counter.increase();
    counter.decrease();
    int actual = counter.getCurrentValue();
    int expectation = 1;
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
    Counter counter = new Counter();
    counter.increase();
    int actual = counter.getCurrentValue();
    int expectation = 1;
    assertEquals(expectation, actual);
  }

  @Test
  void testSetToZero() {
    Counter counter = new Counter();
    counter.increase();
    counter.increase();
    counter.setToZero();
    int actual = counter.getCurrentValue();
    int expectation = 0;
    assertEquals(expectation, actual);
  }
}
