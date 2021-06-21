package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Scanner;

public class Typeracer {

  private GameState state;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  Scanner sc = new Scanner(System.in);

  String input = sc.next();

  char ch[] = input.toCharArray();

  char myChar = ch[0];

  public void addPropertyChangeListener(final PropertyChangeListener changeListener) {
    support.addPropertyChangeListener(changeListener);
  }

  private void notifyListeners() {
    support.firePropertyChange("GameState", null, this.getState());
  }

  public GameState getState() {
    return state;
  }

  public boolean check(char guessedCharacter) {
    if (state.getCurrentGamePhase() != GamePhase.RUNNING) {
      throw new IllegalStateException("Game not running.");
    }

    boolean isCharCorrect = state.getTypeChar().checkChar(guessedCharacter);
    if(state.getTypeChar().checkFinish()){
      state.endGame();
    }

    notifyListeners();
    return isCharCorrect;
  }

  public void forfeit() {
    state.endGame();
    notifyListeners();
  }
}
