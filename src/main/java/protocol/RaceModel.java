package protocol;

import java.util.List;

/**
 * Json model including all race information.
 */
public class RaceModel {

  public final String textToType;

  public final List<String> players;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   */
  public RaceModel(String textToType, List<String> players) {
    this.textToType = textToType;
    this.players = players;
  }

}
