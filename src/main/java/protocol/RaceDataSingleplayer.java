package protocol;

import database.TextDatabase;

public class RaceDataSingleplayer {

  public long startTime;
  public String name;
  public String iconId;
  public String textToType;

  public RaceDataSingleplayer(String textToType, long startTime, String username, String iconId) {
    this.startTime = startTime;
    this.name = username;
    this.iconId = iconId;
    this.textToType = textToType;
  }
}
