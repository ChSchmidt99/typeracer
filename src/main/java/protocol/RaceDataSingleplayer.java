package protocol;

public class RaceDataSingleplayer {

  public String textToType;
  public long startTime;
  public String name;
  public String iconId;

  public RaceDataSingleplayer(String textToType, long startTime, String username, String iconId) {
    this.textToType = textToType;
    this.startTime = startTime;
    this.name = username;
    this.iconId = iconId;
  }
}
