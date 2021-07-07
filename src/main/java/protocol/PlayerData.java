package protocol;

/** Representing JSON Model of a Player. */
public class PlayerData {

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
  public PlayerData(String userId, String name, String iconId) {
    this.name = name;
    this.userId = userId;
    this.iconId = iconId;
  }
}
