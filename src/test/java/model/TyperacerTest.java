package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for Typeracer class. */
class TyperacerTest {

  @Test
  void testCreate() {
    Typeracer tp;
    String expectation = "new WordDatabase().getWord()";
    tp = Typeracer.create();
    String actual = tp.getState().getTypeChar().getCompleteText();
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckCorrect() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('t');
    CorrectionStates expectation = CorrectionStates.CORRECT;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckAutocorrected() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('z');
    CorrectionStates expectation = CorrectionStates.AUTOCORRECTED;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckIncorrect() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('x');
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(actual, expectation);
  }

  @Test
  void testCheckUpperCaseAndLowerCase() {
    Typeracer tp = new Typeracer("test");
    CorrectionStates actual = tp.check('Z');
    CorrectionStates expectation = CorrectionStates.INCORRECT;
    assertEquals(actual, expectation);
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
