package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/** Represents a reverse Typeracer game mode. */
public class TyperacerReverseMode implements TyperacerInterface {
  private final GameState state;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  TyperacerReverseMode(final String text) {

    state = new GameState(text);
  }

  public void addPropertyChangeListener(final PropertyChangeListener changeListener) {
    support.addPropertyChangeListener(changeListener);
  }

  /**
   * Creates a new game.
   *
   * @return new TyperacerReverseMode game.
   */
  public static TyperacerReverseMode create() {
    String randomText = "new WordDatabase().getWord()";
    StringBuilder reversedText = new StringBuilder();
    reversedText.append(randomText);
    reversedText.reverse();
    return new TyperacerReverseMode(reversedText.toString());
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
