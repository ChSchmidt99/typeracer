package database;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestDatabase implements Database {

  private ArrayList<String> database;

  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  @Override
  public String registerUser(String username) throws IOException {
    Path path = Paths.get(String.valueOf(this.getClass().getResource("database.txt")));

    //    Files.writeString(
    //        Paths.get(String.valueOf(path)),
    //
    //        (username + System.lineSeparator())
    //        StandardCharsets.UTF_8,
    //        StandardOpenOption.APPEND);
    return path.toString();
  }

  @Override
  public String getUsername(String userId) {
    return "Cooler Mensch";
  }

  public static void main(String[] args) throws IOException {
    TestDatabase m = new TestDatabase();
    System.out.println(m.registerUser("amazing dude"));
  }
}
