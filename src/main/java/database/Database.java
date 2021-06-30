package database;

import java.io.IOException;

/** interface implements key functions to retrieve text, sore username and retrieve it. */
public interface Database {

  /** select random piece of text from dictionary and use it in the game. */
  String getTextToType();

  /**
   * Creates new database.txt entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  String registerUser(String username) throws Exception;

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   */
  String getUsername(String userId) throws IOException;
}
