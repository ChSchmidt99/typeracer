package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for TyperacerReverseMode class. */
public class TyperacerReverseModeTest {
  @Test
  void testCreateReverseString() {
    TyperacerReverseMode tp;
    String expectation = ")(droWteg.)(esabataDdroW wen";
    tp = TyperacerReverseMode.create();
    String actual = tp.getState().getTypeChar().getCompleteText();
    assertEquals(expectation, actual);
  }
}
