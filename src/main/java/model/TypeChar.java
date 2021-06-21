package model;

import java.util.ArrayList;

public class TypeChar {

  private String completeText;
  private Counter counter;

  GuessChar[] checkedCharacters;

  TypeChar(final String text, Counter counter) {
    completeText = text;
    this.counter = counter;

    checkedCharacters = new GuessChar[completeText.length()];

    for (int i = 0; i < completeText.length(); i++) {

      checkedCharacters[i] = new GuessChar();
    }
  }

  boolean checkChar(char userInput) {
    boolean guessed = false;

    char a = completeText.charAt(counter.getCurrentValue());

    if (userInput == a) {
      guessed = true;
      checkedCharacters[counter.getCurrentValue()] = new GuessChar(a);
      return guessed;
    }
    return guessed;
  }

  boolean checkFinish() {
    if (checkedCharacters[completeText.length() - 1] == null) {

      return false;
    }
    return true;
  }
}
