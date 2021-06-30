package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


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
  void testCheck() {
    Typeracer tp = new Typeracer("test");
    boolean actual = tp.check('t');
    assertTrue(actual);
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
