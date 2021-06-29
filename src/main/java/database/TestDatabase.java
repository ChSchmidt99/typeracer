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

    URL paths = this.getClass().getClassLoader().getResource("database.txt");
    File f = new File(paths.getPath());

    Scanner scanner = new Scanner(f, StandardCharsets.UTF_8);

    while (scanner.hasNextLine()) {
      String[] columns = scanner.nextLine().split(" ");
      map.put(columns[columns.length - 1], columns[0]);
    }

    String output = "";
    for (Map.Entry<String, String> item : map.entrySet()) {
      String key = item.getKey();
      String value = item.getValue();
      if (value.equals(username)) {
        output = key;
      }
    }

    return output;
  }

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   */
  @Override
  public String getUsername(String userId) {

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
    System.out.println(m.registerUser("dude"));
    System.out.println(m.registerUser("random"));
    System.out.println(m.getUsername("24323818-a4f7-44a1-aeea-f3c0a299ca2f"));
    System.out.println(m.getUsername("1763146a-b69a-4eed-9193-5e5878fa886a"));
  }
}
