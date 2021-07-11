package protocol;

import java.util.List;

/** Json model including all race information. */
public class RaceData {

  public final String textToType;

  public final List<UserData> players;

  public final long startTime;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   * @param players list of players
   * @param startTime timestamp of race start as unix epoch long in sec
   */
  public RaceData(String textToType, List<UserData> players, long startTime) {
    this.textToType = textToType;
    this.players = players;
    this.startTime = startTime;
  }
}
