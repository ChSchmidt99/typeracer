package protocol;

import java.util.List;

/** Represents a Json Response. */
public class Response {

  /** All possible Response types. */
  public static class Types {
    public static final String ERROR = "error";
    public static final String LOBBY_UPDATE = "lobby update";
    public static final String GAME_STARTING = "race starting";
    public static final String REGISTERED = "registered";
    public static final String OPEN_LOBBIES = "open lobbies";
    public static final String RACE_UPDATE = "race update";
    public static final String CHECKERED_FLAG = "checkered flag";
    public static final String RACE_RESULT = "race result";
    public static final String CHAT_HISTORY = "chat history";
  }

  public final String type;

  public String message;

  public String userId;

  public LobbyData lobby;

  public RaceData race;

  public List<LobbyData> lobbies;

  public List<PlayerUpdate> playerUpdates;

  public Long raceStop;

  public RaceResult raceResult;

  public List<ChatMessageData> chatHistory;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Response(String type) {
    this.type = type;
  }
}
