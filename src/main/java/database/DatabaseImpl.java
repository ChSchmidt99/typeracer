package database;

import java.io.IOException;

/**
 * Implementation of {@link Database} Interface, connects all Database classes.
 */
public class DatabaseImpl implements Database {

  private final TextDatabase textDatabase;
  private final TestDatabase testDatabase;

  public DatabaseImpl() throws Exception {
    this.testDatabase = new TestDatabase();
    this.textDatabase = new TextDatabase();
  }

  @Override
  public String getTextToType() {
    return this.textDatabase.getPhrase();
  }

  @Override
  public String registerUser(String username) throws IOException {
    return this.testDatabase.registerUser(username);
  }

  @Override
  public String getUsername(String userId) throws IOException {
    return this.testDatabase.getUsername(userId);
  }

}
