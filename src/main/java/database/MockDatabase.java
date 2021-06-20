package database;

public class MockDatabase implements Database{
  @Override
  public String getTextToType() {
    return "Hallo Welt";
  }

  @Override
  public String registerUser(String username) {
    return "Some ID";
  }

  @Override
  public String getUsername(String userId) {
    return "Cooler Mensch";
  }
}
