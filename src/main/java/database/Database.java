package database;

import java.io.IOException;

/** interface implements key functions to retrieve text, sore username and retrieve it. */
public interface Database {

  /**
   * select random piece of text from dictionary and use it in the game.
   *
   * @return text to type
   */
  String getTextToType();

  /**
   * Creates new database.txt entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  String registerUser(String username) throws IOException;

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   * @throws IOException when username cannot be loaded
   */
  String getUsername(String userId) throws IOException;
}
