package database;

import java.io.IOException;

public interface Database {

  String getTextToType();

  /**
   * Creates new database entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  String registerUser(String username) throws IOException;

  String getUsername(String userId);
}
