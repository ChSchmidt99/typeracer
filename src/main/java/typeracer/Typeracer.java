package typeracer;

import database.TextDatabase;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

/** Represents a Typeracer game. Serves as an interface for e.g. the UI. */
public class Typeracer {

  private final GameState state;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  public Typeracer(final String text) {
    state = new GameState(text);
  }

  public void addPropertyChangeListener(final PropertyChangeListener changeListener) {
    support.addPropertyChangeListener(changeListener);
  }

  public static Typeracer create() throws IOException {
    String randomText = new TextDatabase().getPhrase();
    return new Typeracer(randomText);
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
}
