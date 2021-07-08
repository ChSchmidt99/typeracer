package app.model;

public class FinishedMessage {

  private final String mainMessage;
  private final String subMessage;

  FinishedMessage(String main, String sub) {
    this.mainMessage = main;
    this.subMessage = sub;
  }

  public String getMainMessage() {
    return mainMessage;
  }

  public String getSubMessage() {
    return subMessage;
  }
}
