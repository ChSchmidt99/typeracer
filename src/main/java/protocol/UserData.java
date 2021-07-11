package protocol;

/** Representing JSON Model of a User. */
public class UserData {

  public final String name;

  public final String userId;

  public final String iconId;

  public final String state;

  /**
   * Create new user Model.
   *
   * @param userId id of user
   * @param name of user
   * @param iconId local Icon Id
   * @param state the users state
   */
  public UserData(String userId, String name, String iconId, String state) {
    this.name = name;
    this.userId = userId;
    this.iconId = iconId;
    this.state = state;
  }
}
