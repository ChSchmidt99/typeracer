package model;

/** Represent a text that needs to be written. */
public class TextToType {

  private final String completeText;
  private final Counter counter;
  private int mistakeCounter = 0;

  CorrectionStates[] checkedCharacters;

  TextToType(final String text, Counter counter) {
    completeText = text;
    this.counter = counter;

    checkedCharacters = new CorrectionStates[completeText.length()];
  }

  boolean checkChar(char userInput) {
    boolean guessed = false;

    char a = completeText.charAt(counter.getCurrentValue());

    if (userInput == a) {
      guessed = true;
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.CORRECT;
      counter.increase();
    } else {
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.INCORRECT;
      mistakeCounter++;
    }
    return guessed;
  }

  boolean checkFinish() {
    return checkedCharacters[completeText.length() - 1] != null;
  }

  public String getCompleteText() {
    return completeText;
  }

  public int getCounter() {return counter.getCurrentValue();}

  public int getMistakeCounter() {return mistakeCounter;}
}
