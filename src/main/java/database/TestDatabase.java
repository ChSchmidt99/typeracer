package database;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** implements test for the database interface. */
public class TestDatabase {

  Map<String, String> map = new HashMap<>();
  // private final String path;
  // private InputStream in;

  TestDatabase() throws URISyntaxException {
    // URL url = this.getClass().getResource("/database.txt");
    // in = this.getClass().getResourceAsStream("/database.txt");
    // URI uri = url.toURI();
    // System.out.println("Got uri: " + uri);
    // this.path = Paths.get(uri).toString();
    // System.out.println("Got path: " + this.path);
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
    /*
     Files.writeString(
         Paths.get(path),
         (username + " " + uuidAsString + System.lineSeparator()),
         StandardCharsets.UTF_8,
         StandardOpenOption.APPEND);
    */
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
    /*
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
     */
  }
}
