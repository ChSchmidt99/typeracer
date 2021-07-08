package backend;

import protocol.Response;
import protocol.UserData;
import server.PushService;

class User {

  private final String userId;
  private final String name;
  private final String iconId;
  private final String connectionId;
  private final PushService pushService;
  private String state;

  static class State {
    public static final String IN_RACE = "in race";
    public static final String READY = "ready";
    public static final String NOT_READY = "not ready";
    public static final String UNKNOWN = "unkown";
  }

  User(String userId, String name, String iconId, String connectionId, PushService pushService) {
    this.userId = userId;
    this.name = name;
    this.iconId = iconId;
    this.state = State.UNKNOWN;
    this.connectionId = connectionId;
    this.pushService = pushService;
  }

  void sendResponse(Response response) {
    pushService.sendResponse(connectionId, response);
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
