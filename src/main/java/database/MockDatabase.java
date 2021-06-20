package database;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MockDatabase implements Database {

  private ArrayList<String> database;

  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  @Override
  public String registerUser(String username) throws IOException {
    URL path = this.getClass().getResource("database.txt");
    Files.writeString(
        Paths.get(String.valueOf(path)),
        username,
        StandardCharsets.UTF_8,
        StandardOpenOption.APPEND);
    return "Some ID";
  }

  @Override
  public String getUsername(String userId) {
    return "Cooler Mensch";
  }

  public static void main(String[] args) throws IOException {
    MockDatabase m = new MockDatabase();
    System.out.println(m.registerUser("amazing dude"));
  }
}
