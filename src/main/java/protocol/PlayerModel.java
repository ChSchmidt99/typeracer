package protocol;

/** Representing JSON Model of a Player. */
public class PlayerModel {

  public final String name;

  public final String userId;

  public final String iconId;

  public PlayerModel(String userId, String name, String iconId) {
    this.name = name;
    this.userId = userId;
    this.iconId = iconId;
  }
}
