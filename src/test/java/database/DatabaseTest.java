package database;

import database.TestDatabase;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Database unit tests.
 */
public class DatabaseTest {
  @Test
  void getValidUserId() throws Exception {
    TestDatabase t = new TestDatabase();

    String name = "dude";
    String userId = t.registerUser(name);
    String username = t.getUsername(userId);
    Assertions.assertEquals(username, name);
  }
}
