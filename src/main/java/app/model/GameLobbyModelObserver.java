package app.model;

import protocol.ChatMessageData;
import protocol.RaceData;

import java.util.List;

/** Observer for GameLobbyModel. */
public interface GameLobbyModelObserver {

  void updatedLobby();

  void startedRace(RaceData model);

  void receivedError(String message);

  void receivedChatHistory(List<ChatMessageData> chatHistory);
}
