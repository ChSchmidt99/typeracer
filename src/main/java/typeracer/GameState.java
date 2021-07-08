package typeracer;

/** Represents the internal state of the game. */
public class GameState {
  private GamePhase currentGamePhase;
  private final TextToType textToType;

  GameState(final String text) {
    Counter counter = new Counter();
    textToType = new TextToType(text, counter);

    currentGamePhase = GamePhase.RUNNING;
  }

  /** Updates the state to represent a finished/forfeited game. */
  void endGame() {
    currentGamePhase = GamePhase.FINISHED;
  }

  /**
   * Return the current phase of the game.
   *
   * @param currentGamePhase the current phase.
   */
  void setCurrentGamePhase(GamePhase currentGamePhase) {
    this.currentGamePhase = currentGamePhase;
  }

  public GamePhase getCurrentGamePhase() {
    return currentGamePhase;
  }

  public TextToType getTypeChar() {
    return textToType;
  }
}
