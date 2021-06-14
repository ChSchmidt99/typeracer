package protocol;

/**
 * Factory used for creating {@link Response} instances.
 */
public class ResponseFactory {

  /**
   * Make Error response.
   *
   * @param message error message
   * @return {@link Response}
   */
  public static Response makeErrorResponse(String message) {
    Response response = new Response(ResponseTypes.ERROR);
    response.message = message;
    return response;
  }

}
