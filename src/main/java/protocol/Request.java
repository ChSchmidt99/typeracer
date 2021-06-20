package protocol;

/**
 * Represents a Json Request.
 */
public class Request {

  /**
   * All possible Request types.
   */
  public static class Types {
    public static final String REGISTER = "register";
    public static final String NEW_LOBBY = "new lobby";
    public static final String JOIN_LOBBY = "join lobby";
    public static final String LEAVE_LOBBY = "leave lobby";
    public static final String START_RACE = "start race";
    public static final String GET_LOBBIES = "get lobbies";
  }

  public final String type;

  public String userId;

  public String playerName;

  public String lobbyId;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Request(String type) {
    this.type = type;
  }
}
