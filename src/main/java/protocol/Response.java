package protocol;

import java.util.List;

/**
 * Represents a Json Response.
 */
public class Response {

  /**
   * All possible Response types.
   */
  public static class Types {
    public static final String ERROR = "error";
    public static final String LOBBY_UPDATE = "lobby update";
    public static final String GAME_STARTING = "game starting";
    public static final String REGISTERED = "registered";
    public static final String OPEN_LOBBIES = "open lobbies";
  }

  public final String type;

  public String message;

  public String userId;

  public LobbyModel lobby;

  public RaceModel race;

  public List<LobbyModel> lobbies;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Response(String type) {
    this.type = type;
  }
}
