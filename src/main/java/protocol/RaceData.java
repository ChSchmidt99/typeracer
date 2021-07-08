package protocol;

import java.util.List;

/** Json model including all race information. */
public class RaceData {

  public final String textToType;

  public final List<UserData> players;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   * @param players list of players
   */
  public RaceData(String textToType, List<UserData> players) {
    this.textToType = textToType;
    this.players = players;
  }
}
