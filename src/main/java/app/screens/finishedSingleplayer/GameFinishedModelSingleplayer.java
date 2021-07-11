package app.screens.finishedSingleplayer;

/** Model for GameFinished View. */
public class GameFinishedModelSingleplayer {

  private final String username;
  private final String iconId;
  private final Long duration;

  public GameFinishedModelSingleplayer(String username, String iconId, Long duration) {
    this.username = username;
    this.iconId = iconId;
    this.duration = duration;
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
}
