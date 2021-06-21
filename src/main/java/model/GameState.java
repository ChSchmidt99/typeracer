package model;

public class GameState {
  private GamePhase currentGamePhase;
  private Counter counter;
  private TypeChar typeChar;

  GameState(final String text) {
    counter = new Counter();
    typeChar = new TypeChar(text,counter);

    currentGamePhase = GamePhase.RUNNING;
  }

  //void handleIncorrectGuess() {  fehlercheck


  protected void setCurrentGamePhase(GamePhase currentGamePhase) {
    this.currentGamePhase = currentGamePhase;
  }

  public GamePhase getCurrentGamePhase() {
    return currentGamePhase;
  }
  public TypeChar getTypeChar() {
    return typeChar;
  }

  void endGame() {
    currentGamePhase = GamePhase.FINISHED;
  }
}
