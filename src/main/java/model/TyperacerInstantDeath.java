package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** Represents a Typeracer game mode which ends on the first mistake. */
public class TyperacerInstantDeath implements TyperacerInterface {
  private final GameState state;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  TyperacerInstantDeath(final String text) {
    state = new GameState(text);
  }

  public void addPropertyChangeListener(final PropertyChangeListener changeListener) {
    support.addPropertyChangeListener(changeListener);
  }

  public static TyperacerInstantDeath create() {
    String randomText = "new WordDatabase().getWord()";
    return new TyperacerInstantDeath(randomText);
  }

  private void notifyListeners() {
    support.firePropertyChange("GameState", null, this.getState());
  }

  public GameState getState() {
    return state;
  }

  /**
   * Check the given character. This method can only be called on an active game. Otherwise, an
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
    if (result.getState() == CorrectionStates.INCORRECT) {
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
