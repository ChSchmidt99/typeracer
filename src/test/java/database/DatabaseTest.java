package database;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Database unit tests. */
public class DatabaseTest {
  @Test
  void getValidUserId() {
    try {
      TestDatabase t = new TestDatabase();
      String name = "dude";
      String userId = t.registerUser(name);
      String username = t.getUsername(userId);
      Assertions.assertEquals(username, name);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }
}
