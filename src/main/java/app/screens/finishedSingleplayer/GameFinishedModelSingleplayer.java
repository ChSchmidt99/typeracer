package app.screens.finishedSingleplayer;

/** Model for GameFinished View. */
public class GameFinishedModelSingleplayer {

  private final String username;
  private final String iconId;
  private final Long duration;
  private final int wpm;
  private final double acc;

  public GameFinishedModelSingleplayer(
      String username, String iconId, Long duration, int wpm, double acc) {
    this.username = username;
    this.iconId = iconId;
    this.duration = duration;
    this.wpm = wpm;
    this.acc = acc;
  }

  String getUsername() {
    return this.username;
  }

  long getDuration() {
    return this.duration;
  }

  String getIconId() {
    return this.iconId;
  }

  int getWpm() {
    return this.wpm;
  }

  double getAcc() {
    return this.acc;
  }
}
