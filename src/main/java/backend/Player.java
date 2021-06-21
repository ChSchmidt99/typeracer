package backend;

class Player {

  private boolean isReady;
  private final String userId;
  private final String name;

  Player(String userId, String name) {
    this.isReady = false;
    this.userId = userId;
    this.name = name;
  }

  void setIsReady(boolean isReady) {
    this.isReady = isReady;
  }

  String getUserId() {
    return userId;
  }

  String getName() {
    return name;
  }

  boolean getIsReady() {
    return isReady;
  }

}
