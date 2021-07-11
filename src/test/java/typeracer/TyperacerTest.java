package typeracer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for Typeracer class. */
class TyperacerTest {

  @Test
  void testCheckCorrect() {
    Typeracer tp = new Typeracer("test");
    CheckResult actual = tp.check('t');
    CheckResult expectation = new CheckResult(CorrectionStates.CORRECT, 't', 't', 0);
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckAutocorrected() {
    Typeracer tp = new Typeracer("test");
    CheckResult actual = tp.check('z');
    CheckResult expectation = new CheckResult(CorrectionStates.AUTOCORRECTED, 'z', 't', 0);
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckIncorrect() {
    Typeracer tp = new Typeracer("test");
    CheckResult actual = tp.check('x');
    CheckResult expectation = new CheckResult(CorrectionStates.INCORRECT, 'x', 't', 0);
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testCheckUpperCaseAndLowerCase() {
    Typeracer tp = new Typeracer("test");
    CheckResult actual = tp.check('Z');
    CheckResult expectation = new CheckResult(CorrectionStates.INCORRECT, 'Z', 't', 0);
    assertEquals(actual.getState(), expectation.getState());
  }

  @Test
  void testForfeit() {
    Typeracer tp = new Typeracer("");
    GamePhase expectation = GamePhase.FINISHED;
    tp.forfeit();
    GamePhase actual = tp.getState().getCurrentGamePhase();
    assertEquals(expectation, actual);
  }
}
