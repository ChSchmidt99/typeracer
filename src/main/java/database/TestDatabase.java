package database;

import util.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
  private final String path;

  TestDatabase() throws URISyntaxException {
    URI uri = this.getClass().getClassLoader().getResource("database.txt").toURI();
    this.path = Paths.get(uri).toString();
  }


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
    UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    Files.writeString(
            Paths.get(path),
            (username + " " + uuidAsString + System.lineSeparator()),
            StandardCharsets.UTF_8,
            StandardOpenOption.APPEND);

    return uuidAsString;
  }

  /**
   * Retrieves username by id.
   *
   * @param userId name of user
   * @return userId
   */
  @Override
  public String getUsername(String userId) throws IOException {
    File f = new File(path);
    Scanner scanner = new Scanner(f, StandardCharsets.UTF_8);

    while (scanner.hasNextLine()) {
      String[] columns = scanner.nextLine().split(" ");
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
}
