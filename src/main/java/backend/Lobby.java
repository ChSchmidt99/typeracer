package backend;

import database.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import protocol.ChatMessageData;
import protocol.LobbyData;
import protocol.Response;
import protocol.ResponseFactory;
import protocol.UserData;
import util.Logger;
import util.Timestamp;

/** Represents one game currently managed by the server. */
class Lobby implements RaceFinishedListener {

  private static final int START_DELAY = 3;
  private static final int MAX_PLAYERS = 4;

  private final String lobbyId;
  private final List<User> members;
  private final Database database;
  private final String name;

  // Finished races were planned to be stored in database, but feature was not finished in time.
  // That's the only reason finished races are stored in Lobby for now.
  private final List<Race> finishedRaces;
  private final List<ChatMessage> chatHistory;
  private Race race;

  Lobby(String lobbyId, String name, Database database) {
    this.members = new ArrayList<>();
    this.lobbyId = lobbyId;
    this.database = database;
    this.finishedRaces = new ArrayList<>();
    this.chatHistory = new ArrayList<>();
    this.name = name;
  }

  @Override
  public void raceFinished() {
    broadcastLobbyUpdate();
    finishedRaces.add(race);
    Response resultResponse = ResponseFactory.makeRaceResultResponse(race.getRaceResult());
    broadcast(resultResponse);
  }

  void join(User user) {
    if (members.size() < MAX_PLAYERS) {
      members.add(user);
      broadcastLobbyUpdate();
    } else {
      Response error = ResponseFactory.makeErrorResponse("Max number of players.");
      user.sendResponse(error);
    }
  }

  void leave(User user) {
    if (!members.contains(user)) {
      Logger.logError("Called leave on user that's not in lobby");
      return;
    }
    if (user.getState().equals(User.State.IN_RACE)) {
      this.race.removePlayer(user);
    }
    members.remove(user);
    user.setState(User.State.UNKNOWN);
    broadcastLobbyUpdate();
  }

  void sendChatMessage(ChatMessage message) {
    chatHistory.add(message);
    broadcastChat();
  }

  void startRace(User user, RaceSettings settings) {
    HashMap<String, Player> readyPlayers = collectReadyPlayers();
    if (readyPlayers.size() == 0) {
      Response error = ResponseFactory.makeErrorResponse("No players ready");
      user.sendResponse(error);
      return;
    }
    this.race =
        new Race(
            settings,
            this.database.getTextToType(),
            readyPlayers,
            this,
            Timestamp.currentTimestamp() + START_DELAY);
    broadcastLobbyUpdate();
  }

  LobbyData lobbyModel() {
    List<UserData> playerData = new ArrayList<>();
    for (User user : members) {
      playerData.add(user.getUserData());
    }
    return new LobbyData(lobbyId, playerData, name, isRunning());
  }

  void setPlayerReady(User user, boolean isReady) {
    if (isReady) {
      user.setState(User.State.READY);
    } else {
      user.setState(User.State.NOT_READY);
    }
    broadcastLobbyUpdate();
  }

  boolean isRunning() {
    if (this.race == null) {
      return false;
    }
    return this.race.getIsRunning();
  }

  Race getRace() {
    return this.race;
  }

  String getLobbyId() {
    return this.lobbyId;
  }

  boolean isEmpty() {
    return this.members.isEmpty();
  }

  void sendUpdate(User user) {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    user.sendResponse(response);
  }

  void sendChatHistory(User user) {
    Response chatHistory = ResponseFactory.makeChatResponse(marshalChatHistory());
    user.sendResponse(chatHistory);
  }

  void sendPreviousRaceResult(User user) {
    if (finishedRaces.size() == 0) {
      Logger.logError("Tried retrieving non existing previous race");
      return;
    }
    Race race = finishedRaces.get(finishedRaces.size() - 1);
    Response response = ResponseFactory.makeRaceResultResponse(race.getRaceResult());
    user.sendResponse(response);
  }

  private HashMap<String, Player> collectReadyPlayers() {
    HashMap<String, Player> readyPlayers = new HashMap<>();
    for (User user : members) {
      if (user.getState().equals(User.State.READY)) {
        readyPlayers.put(user.getId(), new Player(user));
        user.setState(User.State.IN_RACE);
      }
    }
    return readyPlayers;
  }

  private void broadcast(Response response) {
    for (User user : members) {
      user.sendResponse(response);
    }
  }

  private void broadcastChat() {
    List<ChatMessageData> messages = marshalChatHistory();
    Response response = ResponseFactory.makeChatResponse(messages);
    broadcast(response);
  }

  private List<ChatMessageData> marshalChatHistory() {
    List<ChatMessageData> messages = new ArrayList<>();
    for (ChatMessage message : this.chatHistory) {
      messages.add(message.toChatMessageData());
    }
    return messages;
  }

  private void broadcastLobbyUpdate() {
    Response response = ResponseFactory.makeLobbyUpdateResponse(lobbyModel());
    broadcast(response);
  }
}
