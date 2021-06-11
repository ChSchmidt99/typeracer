package requests;

/**
 * Example Request for testing.
 */
public class Request {

  public final String type;

  public String userId;

  public String playerName;

  public String gameId;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Request(String type) {
    this.type = type;
  }
}
