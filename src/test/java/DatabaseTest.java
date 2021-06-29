import database.TestDatabase;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
  @Test
  void getValidUserID() throws IOException {
    TestDatabase t = new TestDatabase();

    String name = "dude";
    String userID = t.registerUser(name);
    String username = t.getUsername(userID);
    Assertions.assertEquals(username, name);
  }
}
