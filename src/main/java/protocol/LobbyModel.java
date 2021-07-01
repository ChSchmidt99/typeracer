package protocol;

import java.util.List;

/**
 * Json model for all lobby information.
 */
public class LobbyModel {

  public String id;

  public String name;

  public List<String> players;

  public boolean isRunning;

  /**
   * Create with given information.
   *
   * @param id lobby id
   * @param players connected players
   * @param name lobby name
   * @param isRunning whether or not a race is currently running
   */
  public LobbyModel(String id, List<String> players, String name, boolean isRunning) {
    this.id = id;
    this.players = players;
    this.name = name;
    this.isRunning = isRunning;
  }
}
