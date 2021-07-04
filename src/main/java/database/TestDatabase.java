package database;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

/** implements test for the database interface. */
public class TestDatabase {

  Map<String, String> map = new HashMap<>();
  private final String path;

  TestDatabase() throws URISyntaxException {
    URL url = Objects.requireNonNull(this.getClass().getClassLoader().getResource("database.txt"));
    this.path = Paths.get(url.toURI()).toString();
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
  String getUsername(String userId) throws IOException {

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

    // TODO: Maybe find a nicer solution
    if (output.equals("")) {
      output = "unknown";
    }
    return output;
  }
}
