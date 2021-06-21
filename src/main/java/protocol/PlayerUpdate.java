package protocol;

/**
 * Used to create a JSON containing player updates.
 */
public class PlayerUpdate {

  public final String userId;

  public final int wpm;

  public final float percentProgress;

  /**
   * Create PlayerUpdate.
   *
   * @param userId id of user
   * @param wpm words per minute
   * @param percentProgress progress in range [0,1]
   */
  public PlayerUpdate(String userId, int wpm, float percentProgress) {
    this.userId = userId;
    this.wpm = wpm;
    this.percentProgress = percentProgress;
  }
}
