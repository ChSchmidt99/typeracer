package protocol;

/**
 * Used to create a JSON containing player updates.
 */
public class PlayerUpdate {

  public final String userId;

  public final int wpm;

  public final float percentProgress;

  public final Boolean isFinished;

  public final long raceDuration;

  //TODO: pass in player instead of user ID?
  /**
   * Create PlayerUpdate.
   *
   * @param userId id of user
   * @param wpm words per minute
   * @param percentProgress progress in range [0,1]
   */
  public PlayerUpdate(String userId, int wpm, float percentProgress, boolean isFinished,
                      long duration) {
    this.userId = userId;
    this.wpm = wpm;
    this.percentProgress = percentProgress;
    this.isFinished = isFinished;
    this.raceDuration = duration;
  }
}
