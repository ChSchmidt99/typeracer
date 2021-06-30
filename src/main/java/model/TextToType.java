package model;

/** Represent a text that needs to be written. */
public class TextToType {

  private final String completeText;
  private final Counter counter;

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
    } else {
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.INCORRECT;
    }
    return guessed;
  }

  boolean checkFinish() {
    return checkedCharacters[completeText.length() - 1] != null;
  }
}
