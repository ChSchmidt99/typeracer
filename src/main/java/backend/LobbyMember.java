package backend;

/** Used to represent a User connected to a lobby. */
class LobbyMember {

  private boolean isReady;
  // TODO: Collect Userinfo in abstract class
  private final String userId;
  private final String connectionId;
  private final String name;
  private final String iconId;
  private boolean inRace;

  /**
   * Create a new LobbyMember.
   *
   * @param userId id of user
   * @param name name of user
   */
  LobbyMember(String userId, String connectionId, String name, String iconId) {
    this.isReady = false;
    this.userId = userId;
    this.connectionId = connectionId;
    this.name = name;
    this.iconId = iconId;
    this.inRace = false;
  }

  void setIsReady(boolean isReady) {
    this.isReady = isReady;
  }

  String getName() {
    return name;
  }

  boolean getIsReady() {
    return isReady;
  }

  Player toPlayer() {
    return new Player(this.userId, this.connectionId, this.name, this.iconId);
  }

  boolean isInRace() {
    return inRace;
  }

  void setInRace(boolean isInRace) {
    this.inRace = isInRace;
  }
}
