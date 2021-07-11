package database;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** implements test for the database interface. */
public class UserDatabase {

  // Database was initially planned to store users in a file permanently.
  // Because writing to files is not possible from .jar files
  // and no database feature was finished in time, it is now a relatively simple user store.
  Map<String, String> map;

  UserDatabase() throws URISyntaxException {
    this.map = new HashMap<>();
  }

  /**
   * Creates new database.txt entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  String registerUser(String username) throws IOException {
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    this.map.put(uuidAsString, username);
    return uuidAsString;
  }

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   */
  String getUsername(String userId) throws IOException {
    return map.get(userId);
  }
}
