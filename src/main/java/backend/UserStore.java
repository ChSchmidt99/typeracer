package backend;

import java.util.HashMap;

class UserStore {

  private final HashMap<String, User> users;

  UserStore() {
    this.users = new HashMap<>();
  }

  void addNewUser(String connectionId, User user) {
    this.users.put(connectionId, user);
  }

  User getUser(String connectionId) {
    return this.users.get(connectionId);
  }
}
