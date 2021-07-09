package app.screens.lobby;

import java.util.List;
import protocol.ChatMessageData;
import protocol.RaceData;

/** Observer for GameLobbyModel. */
interface GameLobbyModelObserver {

  void updatedLobby();

  void startedRace(RaceData model);

  void receivedError(String message);

  void receivedChatHistory(List<ChatMessageData> chatHistory);
}
