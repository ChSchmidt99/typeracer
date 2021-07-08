package backend;

import protocol.UserData;

class User {

  private final String userId;
  private final String name;
  private final String iconId;
  private String state;

  static class State {
    public static final String IN_RACE = "in race";
    public static final String READY = "ready";
    public static final String NOT_READY = "not ready";
    public static final String UNKNOWN = "unkown";
  }

  User(String userId, String name, String iconId) {
    this.userId = userId;
    this.name = name;
    this.iconId = iconId;
    this.state = State.UNKNOWN;
  }

  String getId() {
    return userId;
  }

  void setState(String state) {
    this.state = state;
  }

  String getState() {
    return this.state;
  }

  UserData getUserData() {
    return new UserData(this.userId, this.name, this.iconId, this.state);
  }
}
