package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit test for end game. */
class GameStateTest {

  @Test
  void testEndGame() {
    GamePhase expectation = GamePhase.FINISHED;
    GameState state = new GameState("test");
    state.endGame();
    GamePhase actual = state.getCurrentGamePhase();
    assertEquals(expectation, actual);
  }
}
