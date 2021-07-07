package protocol;

/** Representing JSON Model of a User. */
public class User {

  public final String name;

  public final String userId;

  public final String iconId;

  /**
   * Create new user Model.
   *
   * @param userId id of user
   * @param name of user
   * @param iconId local Icon Id
   */
  public User(String userId, String name, String iconId) {
    this.name = name;
    this.userId = userId;
    this.iconId = iconId;
  }
}
