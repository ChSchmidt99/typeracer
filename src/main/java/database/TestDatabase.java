package database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class TestDatabase implements Database {

  private ArrayList<String> database;

  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  @Override
  public String registerUser(String username) throws IOException {
    // String path = this.getClass().getClassLoader().getResource("dictionary.txt").getPath();
    String path = this.getClass().getClassLoader().getResource("database.txt").getPath();

    Files.writeString(
        Paths.get(path),
        (username + System.lineSeparator()),
        StandardCharsets.UTF_8,
        StandardOpenOption.APPEND);

    return path.toString();
  }

  @Override
  public String getUsername(String userId) {
    return "Cooler Mensch";
  }

  public static void main(String[] args) throws IOException {
    TestDatabase m = new TestDatabase();
    System.out.println(m.registerUser("amazing dude"));
    System.out.println(m.registerUser("some stuff"));
  }
}
