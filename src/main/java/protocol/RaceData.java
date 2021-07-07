package protocol;

import java.util.List;

/** Json model including all race information. */
public class RaceData {

  public final String textToType;

  public final List<User> players;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   * @param players list of players
   */
  public RaceData(String textToType, List<User> players) {
    this.textToType = textToType;
    this.players = players;
  }
}
