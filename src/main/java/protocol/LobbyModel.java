package protocol;

import java.util.List;

/**
 * Json model for all lobby information.
 */
public class LobbyModel {

  public String id;

  public List<String> players;

  public boolean isRunning;

  /**
   * Create with given information.
   *
   * @param id lobby id
   * @param players connected players
   * @param isRunning whether or not a race is currently running
   */
  public LobbyModel(String id, List<String> players, boolean isRunning) {
    this.id = id;
    this.players = players;
    this.isRunning = isRunning;
  }
}
