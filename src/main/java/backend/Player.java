package backend;

/**
 * Used to represent a Player in an ongoing race.
 */
public class Player {

  private final String userId;
  private final String name;
  private final String connectionId;

  Player(String userId, String connectionId, String name) {
    this.userId = userId;
    this.connectionId = connectionId;
    this.name = name;
  }

  String getConnectionId() {
    return connectionId;
  }

  String getName() {
    return name;
  }

}
