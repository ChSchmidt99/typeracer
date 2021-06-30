package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for Typeracer class. */
class TyperacerTest {

  @Test
  void testCreate() {
    Typeracer tp = new Typeracer("");
    String expectation = "new WordDatabase().getWord()";
    tp = tp.create();
    String actual = tp.getState().getTypeChar().getCompleteText();
    assertEquals(expectation, actual);
  }

  @Test
  void testCheck() {
    boolean expectation = true;
    Typeracer tp = new Typeracer("test");
    boolean actual = tp.check('t');
    assertEquals(expectation, actual);
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
