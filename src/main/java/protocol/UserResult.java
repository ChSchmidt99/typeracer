package protocol;

public class UserResult {

  public final User user;

  public final int wpm;

  public final int mistakes;

  public final int place;

  public UserResult(User user, int wpm, int mistakes, int place) {
    this.user = user;
    this.wpm = wpm;
    this.mistakes = mistakes;
    this.place = place;
  }

}
