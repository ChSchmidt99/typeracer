package app.screens.singleplayer;

/** Represents the message displayed after player is finished. */
class FinishedMessage {

  private final String mainMessage;
  private final String subMessage;

  FinishedMessage(String main, String sub) {
    this.mainMessage = main;
    this.subMessage = sub;
  }

  String getMainMessage() {
    return mainMessage;
  }

  String getSubMessage() {
    return subMessage;
  }
}
