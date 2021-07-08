package model;

/** Represents a Typeracer game. Serves as an interface for e.g. the UI. */
public interface TyperacerInterface {

  GameState getState();

  /**
   * Checks the given character. This method can only be called on an active game.
   *
   * @param guessedCharacter character to guess.
   * @return true if the check was successful. false otherwise.
   */
  CheckResult check(char guessedCharacter);

  /** Forfeit the current game. */
  void forfeit();
}
