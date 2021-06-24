package database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/** implements test for the database interface. */
public class TestDatabase implements Database {

  /** select random piece of text from dictionary and use it in the game. */
  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  /**
   * Creates new database.txt entry for user and returns unique ID.
   *
   * @param username name of user
   * @return userId
   */
  @Override
  public String registerUser(String username) throws IOException {
    String path = this.getClass().getClassLoader().getResource("database.txt").getPath();

    Files.writeString(
        Paths.get(path),
        (username + System.lineSeparator()),
        StandardCharsets.UTF_8,
        StandardOpenOption.APPEND);

    return path;
  }

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   */
  @Override
  public String getUsername(String userId) {
    return "Cooler Mensch";
  }

  /**
   * testing method.
   *
   * @param args provides arguments as in usual main class.
   */
  public static void main(String[] args) throws IOException {
    TestDatabase m = new TestDatabase();
    System.out.println(m.registerUser("amazing dude"));
    System.out.println(m.registerUser("whoever"));
    System.out.println(m.registerUser("random"));
  }
}
