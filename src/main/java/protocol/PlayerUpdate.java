package protocol;

public class PlayerUpdate {

  public final String userId;

  public final int wpm;

  public final float percentProgress;

  public PlayerUpdate(String userId, int wpm, float percentProgress) {
    this.userId = userId;
    this.wpm = wpm;
    this.percentProgress = percentProgress;
  }
}
