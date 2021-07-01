package mocks;

import database.Database;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Mock Database that can be used for testing, only holds Data in RAM. */
public class MockDatabase implements Database {

  private final Map<String, String> names;

  public MockDatabase() {
    this.names = new HashMap<>();
  }

  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  @Override
  public String registerUser(String username) throws IOException {
    UUID id = UUID.randomUUID();
    this.names.put(id.toString(), username);
    return id.toString();
  }

  @Override
  public String getUsername(String userId) throws IOException {
    return names.get(userId);
  }
}
