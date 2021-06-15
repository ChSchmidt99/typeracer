package protocol;

/**
 * Represents a Json Response.
 */
public class Response {

  public final String type;

  public String message;

  public String gameId;

  public boolean isRunning;

  public String playerName;

  public String textToType;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Response(String type) {
    this.type = type;
  }
}
