package typeracer;

/** Represents a Typeracer game. Serves as an interface for e.g. the UI. */
public class Typeracer implements TyperacerInterface {

  private final GameState state;

  public Typeracer(final String text) {
    state = new GameState(text);
  }

  public GameState getState() {
    return state;
  }

  /**
   * Checks the given character. This method can only be called on an active game. Otherwise, an
   * IllegalStateException is thrown.
   *
   * @param guessedCharacter character to guess
   * @return true if the check was successful. false otherwise.
   * @throws IllegalStateException – if the current Typeracer game is not running
   */
  public CheckResult check(char guessedCharacter) throws IllegalStateException {
    if (state.getCurrentGamePhase() != GamePhase.RUNNING) {
      throw new IllegalStateException("Game not running.");
    }

    CheckResult result = state.getTypeChar().checkChar(guessedCharacter);
    if (state.getTypeChar().checkFinish()) {
      state.endGame();
    }

    return result;
  }

  /**
   * Forfeit the current game. This method can only be called on an active game. Otherwise, an
   * IllegalStateException is thrown.
   *
   * @throws IllegalStateException – if the current Typeracer game is not running
   */
  public void forfeit() {
    state.endGame();
  }
}
