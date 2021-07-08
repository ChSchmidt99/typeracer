package protocol;

import java.util.List;

/** Json model for all lobby information. */
public class LobbyData {

  public String id;

  public String name;

  public List<UserData> players;

  public boolean isRunning;

  /**
   * Create with given information.
   *
   * @param id lobby id
   * @param players connected players
   * @param name lobby name
   * @param isRunning whether or not a race is currently running
   */
  public LobbyData(String id, List<UserData> players, String name, boolean isRunning) {
    this.id = id;
    this.players = players;
    this.name = name;
    this.isRunning = isRunning;
  }
}
