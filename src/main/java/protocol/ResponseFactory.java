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

  /**
   * Make joined game response.
   *
   * @param gameId of the joined game
   * @return {@link Response}
   */
  public static Response makeJoinedGameResponse(String gameId, boolean isRunning) {
    Response response = new Response(ResponseTypes.JOINED_GAME);
    response.gameId = gameId;
    response.isRunning = isRunning;
    return response;
  }

  public static Response makePlayerJoinedResponse(String playerName) {
    Response response = new Response(ResponseTypes.PLAYER_JOINED);
    response.playerName = playerName;
    return response;
  }

  public static Response makePlayerLeftResponse(String playerName) {
    Response response = new Response(ResponseTypes.PLAYER_LEFT);
    response.playerName = playerName;
    return response;
  }

  public static Response makeGameStartingResponse(String textToType) {
    Response response = new Response(ResponseTypes.GAME_STARTING);
    response.textToType = textToType;
    return response;
  }

}
