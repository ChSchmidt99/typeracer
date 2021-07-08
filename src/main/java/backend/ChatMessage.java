package backend;

import protocol.ChatMessageData;

class ChatMessage {

  private final User user;
  private final String message;

  ChatMessage(User user, String message) {
    this.user = user;
    this.message = message;
  }

  ChatMessageData toChatMessageData() {
    return new ChatMessageData(user.getUserData(), message);
  }
}
