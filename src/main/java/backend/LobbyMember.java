package backend;

/** Used to represent a User connected to a lobby. */
class LobbyMember {

  private final String connectionId;
  private final User user;

  /**
   * Create a new LobbyMember.
   *
   * @param connectionId id of connection
   * @param user {@link User}
   */
  LobbyMember(String connectionId, User user) {
    this.connectionId = connectionId;
    this.user = user;
  }

  User getUser() {
    return user;
  }

  Player toPlayer() {
    this.user.setState(User.State.IN_RACE);
    return new Player(this.connectionId, this.user);
  }

  boolean isReady() {
    return user.getState().equals(User.State.READY);
  }

  void setReady(boolean isReady) {
    if (isReady) {
      this.user.setState(User.State.READY);
    } else {
      this.user.setState(User.State.NOT_READY);
    }
  }
}
