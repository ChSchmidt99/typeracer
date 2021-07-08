package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** Represents a classic Typeracer game. */
public class Typeracer implements TyperacerInterface {

  private final GameState state;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  Typeracer(final String text) {
    state = new GameState(text);
  }

  public void addPropertyChangeListener(final PropertyChangeListener changeListener) {
    support.addPropertyChangeListener(changeListener);
  }

  /**
   * Creates a new game.
   *
   * @return new Typeracer game.
   */
  public static Typeracer create() {
    String randomText = "new WordDatabase().getWord()";
    return new Typeracer(randomText);
  }

  private void notifyListeners() {
    support.firePropertyChange("GameState", null, this.getState());
  }

  public GameState getState() {
    return state;
  }

  /**
   * Checks the given character. This method can only be called on an active game. Otherwise, an
   * IllegalStateException is thrown.
   *
   * @param guessedCharacter character to guess.
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

    notifyListeners();
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
    notifyListeners();
  }
}
