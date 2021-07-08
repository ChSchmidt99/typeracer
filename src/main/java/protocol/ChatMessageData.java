package protocol;

/** JSON Model for chat messages. */
public class ChatMessageData {

  public final UserData user;
  public final String message;

  public ChatMessageData(UserData user, String message) {
    this.user = user;
    this.message = message;
  }
}
