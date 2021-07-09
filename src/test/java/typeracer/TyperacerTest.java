package typeracer;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for Typeracer class. */
class TyperacerTest {

  @Test
  void testCreate() throws IOException {
    Typeracer tp;
    String expectation = "Ich kann meine Stirn nicht sehen!";
    tp = Typeracer.create();
    String actual = tp.getState().getTypeChar().getCompleteText();
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckCorrect() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('t').getState();
    CorrectionStates expectation = CorrectionStates.CORRECT;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckAutocorrected() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('z').getState();
    CorrectionStates expectation = CorrectionStates.AUTOCORRECTED;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckIncorrect() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('x').getState();
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckUpperCaseAndLowerCase() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('Z').getState();
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(actual, expectation);
  }
}
