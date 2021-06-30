package protocol;

import java.util.List;

/**
 * Json model including all race information.
 */
public class RaceModel {

  public final String textToType;

  public final List<PlayerModel> players;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   */
  public RaceModel(String textToType, List<PlayerModel> players) {
    this.textToType = textToType;
    this.players = players;
  }

}
