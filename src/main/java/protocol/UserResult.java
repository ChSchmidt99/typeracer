package protocol;

/** Holds the result a player achieved in one race. */
public class UserResult {

  public final UserData userData;

  public final int wpm;

  public final int mistakes;

  public final int place;

  /**
   * Create new UserResult.
   *
   * @param userData information about user
   * @param wpm words per minute
   * @param mistakes in race
   * @param place which place the player ended up in
   */
  public UserResult(UserData userData, int wpm, int mistakes, int place) {
    this.userData = userData;
    this.wpm = wpm;
    this.mistakes = mistakes;
    this.place = place;
  }
}
