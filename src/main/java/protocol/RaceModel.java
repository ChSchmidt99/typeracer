package protocol;

/**
 * Json model including all race information.
 */
public class RaceModel {

  public final String textToType;

  /**
   * Create RaceModel.
   *
   * @param textToType text of the race
   */
  public RaceModel(String textToType) {
    this.textToType = textToType;
  }

}
