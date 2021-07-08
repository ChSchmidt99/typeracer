package protocol;

public class UserResult {

  public final UserData userData;

  public final int wpm;

  public final int mistakes;

  public final int place;

  public UserResult(UserData userData, int wpm, int mistakes, int place) {
    this.userData = userData;
    this.wpm = wpm;
    this.mistakes = mistakes;
    this.place = place;
  }
}
