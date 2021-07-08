package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for TyperacerInstantDeath class. */
public class TyperacerInstantDeathTest {
  @Test
  void testCheckIfGameEndsAfterIncorrect() {
    TyperacerInstantDeath tp = new TyperacerInstantDeath("test");
    tp.check('x');
    GamePhase actual = tp.getState().getCurrentGamePhase();
    GamePhase expectation = GamePhase.FINISHED;

    assertEquals(expectation, actual);
  }
}
