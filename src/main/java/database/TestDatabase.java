package database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/** implements test for the database interface. */
public class TestDatabase implements Database {

  Map<String, String> map = new HashMap<>();

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

    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    Files.writeString(
        Paths.get(path),
        (username + " " + uuidAsString + System.lineSeparator()),
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
  public String getUsername(String userId) throws IOException {
    URL path = this.getClass().getClassLoader().getResource("database.txt");
    File f = new File(path.getPath());

    Scanner scanner = new Scanner(f, StandardCharsets.UTF_8);

    while (scanner.hasNextLine()) {
      String[] columns = scanner.nextLine().split(" ");
      int l = columns.length;
      map.put(columns[columns.length - 1], columns[0]);
    }

    String output = "";

    for (Map.Entry<String, String> item : map.entrySet()) {
      String key = item.getKey();
      String value = item.getValue();
      if (key.equals(userId)) {
        output = value;
      }
    }
    return output;
  }

  /**
   * testing method.
   *
   * @param args provides arguments as in usual main class.
   */
  public static void main(String[] args) throws IOException {
    TestDatabase m = new TestDatabase();
    System.out.println(m.registerUser("somebody"));
    System.out.println(m.registerUser("whoever"));
    System.out.println(m.registerUser("random"));
  }
}
