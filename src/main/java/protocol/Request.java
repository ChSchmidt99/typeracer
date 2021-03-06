package protocol;

/** Represents a Json Request. */
public class Request {

  /** All possible Request types. */
  public static class Types {
    public static final String REGISTER = "register";
    public static final String NEW_LOBBY = "new lobby";
    public static final String JOIN_LOBBY = "join lobby";
    public static final String LEAVE_LOBBY = "leave lobby";
    public static final String START_RACE = "start race";
    public static final String PLAYER_READY = "player ready";
    public static final String GET_LOBBIES = "get lobbies";
    public static final String UPDATE_PROGRESS = "update progress";
    public static final String LOBBY_UPDATE = "get lobby update";
    public static final String PREV_RACE_RESULT = "previous race result";
    public static final String CHAT_MESSAGE = "chat message";
    public static final String CHAT_HISTORY = "chat history";
  }

  public final String type;

  public String playerName;

  public String iconId;

  public String lobbyId;

  public String lobbyName;

  public Boolean isReady;

  public ProgressSnapshot snapshot;

  public String message;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Request(String type) {
    this.type = type;
  }
}
