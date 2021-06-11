package protocol;

/**
 * Represents a Json Response.
 */
public class Response {

  public final String type;

  public String message;

  /**
   * Create a Request with given params.
   *
   * @param type request type
   */
  public Response(String type) {
    this.type = type;
  }
}
