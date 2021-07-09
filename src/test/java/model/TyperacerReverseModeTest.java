package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for TyperacerReverseMode class. */
public class TyperacerReverseModeTest {
  @Test
  void testReverseString() {
    TyperacerReverseMode tp;
    tp = new TyperacerReverseMode("abc");
    String expectation = "cba";
    String actual = tp.getState().getTypeChar().getCompleteText();
    assertEquals(expectation, actual);
  }
}
