package protocol;

/** Data for a race in Singleplayer. */
public class RaceDataSingleplayer {

  public long startTime;
  public String name;
  public String iconId;
  public String textToType;

  /**
   * Create new RaceDataSingleplayer.
   *
   * @param textToType text to type for the game
   * @param startTime time of the race start
   * @param username name of user
   * @param iconId icon id
   */
  public RaceDataSingleplayer(String textToType, long startTime, String username, String iconId) {
    this.startTime = startTime;
    this.name = username;
    this.iconId = iconId;
    this.textToType = textToType;
  }
}
