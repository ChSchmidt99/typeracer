package protocol;

public class ResponseFactory {

  public static Response makeErrorResponse(String message) {
    Response response = new Response(ResponseTypes.ERROR);
    response.message = message;
    return response;
  }

}
