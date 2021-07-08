package app.model;

/** Represents the message displayed after player is finished. */
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
