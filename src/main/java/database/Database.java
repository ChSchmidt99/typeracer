package database;

public interface Database {

  String getTextToType();

  /**
   * Creates new database entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  String registerUser(String username);

  String getUsername(String userId);

}
