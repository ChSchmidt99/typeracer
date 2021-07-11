package typeracer;

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

  CheckResult checkChar(char userInput) {
    CorrectionStates guessed = CorrectionStates.INCORRECT;

    char expected = completeText.charAt(counter.getCurrentValue());

    int index = counter.getCurrentValue();

    if (userInput == expected) {
      guessed = CorrectionStates.CORRECT;
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.CORRECT;
      counter.increase();
    } else {
      mistakeCounter++;
      if (checkForAutocorrect(userInput, expected) == expected) {
        guessed = CorrectionStates.AUTOCORRECTED;
        counter.increase();
      }
    }
    return new CheckResult(guessed, userInput, expected, index);
  }

  /**
   * Checks if the user input should be autocorrected or not.
   *
   * @param typedChar user input.
   * @param givenChar database char.
   * @return if givenChar is neighbour of typedChar then returns givenChar else returns typedChar.
   */
  private char checkForAutocorrect(char typedChar, char givenChar) {
    if ((Character.isUpperCase(typedChar) && Character.isLowerCase(givenChar))
        || (Character.isLowerCase(typedChar) && Character.isUpperCase(givenChar))) {
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.INCORRECT;
    }

    boolean isUpper = false;
    if (Character.isUpperCase(typedChar)) {
      isUpper = true;
      typedChar = Character.toLowerCase(typedChar);
      givenChar = Character.toLowerCase(givenChar);
    }

    if (getNeighboringKeys(typedChar).indexOf(givenChar) != -1) {
      if (isUpper) {
        typedChar = Character.toUpperCase(givenChar);
      } else {
        typedChar = givenChar;
      }
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.AUTOCORRECTED;
    } else {
      checkedCharacters[counter.getCurrentValue()] = CorrectionStates.INCORRECT;
    }
    return typedChar;
  }
  // source:
  // https://stackoverflow.com/questions/10275461/java-whats-the-most-efficient-way-to-remove-all-blank-space-from-a-stringbuild

  String chars = "qwertzuiopü asdfghjklöä'yxcvbnm,.-  ";

  /**
   * Method to find neighbouring keys.
   *
   * @param key typed char.
   * @return String of key neighbours.
   */
  public String getNeighboringKeys(char key) {
    StringBuilder result = new StringBuilder();
    for (char c : chars.toCharArray()) {
      if (c != key && distance(c, key) < 2) {
        result.append(c);
      }
    }
    for (int i = 0; i < result.length(); i++) {
      if (Character.isWhitespace(result.charAt(i))) {
        result.deleteCharAt(i);
      }
    }
    return result.toString();
  }

  private double distance(char c1, char c2) {
    return Math.sqrt(Math.pow(colOf(c2) - colOf(c1), 2) + Math.pow(rowOf(c2) - rowOf(c1), 2));
  }

  private int rowOf(char c) {
    return chars.indexOf(c) / 12;
  }

  private int colOf(char c) {
    return chars.indexOf(c) % 12;
  }
  // source:
  // https://stackoverflow.com/questions/10275461/java-whats-the-most-efficient-way-to-remove-all-blank-space-from-a-stringbuild

  boolean checkFinish() {
    return checkedCharacters[completeText.length() - 1] != null
        && checkedCharacters[completeText.length() - 1] != CorrectionStates.INCORRECT;
  }

  public String getCompleteText() {
    return completeText;
  }

  public int getCounter() {
    return counter.getCurrentValue();
  }

  public int getMistakeCounter() {
    return mistakeCounter;
  }
}
