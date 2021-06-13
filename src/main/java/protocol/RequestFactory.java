package protocol;

/**
 * Factory for creating {@link Request} instances.
 */
public class RequestFactory {

  /**
   * Make a Register {@link Request} used to register a new player.
   *
   * @param playerName name of the player
   * @return {@link Request}
   */
  public static Request makeRegisterRequest(String playerName) {
    Request request = new Request(RequestTypes.REGISTER);
    request.playerName = playerName;
    return request;
  }

  /**
   * Make a New game {@link Request} used to create a new game.
   *
   * @param userId of initial player
   * @return {@link Request}
   */
  public static Request makeNewGameRequest(String userId) {
    Request request = new Request(RequestTypes.NEW_GAME);
    request.userId = userId;
    return request;
  }

  /**
   * Make a join game {@link Request} used to join existing games.
   *
   * @param userId of player
   * @param gameId of game
   * @return {@link Request}
   */
  public static Request makeJoinGameRequest(String userId, String gameId) {
    Request request = new Request(RequestTypes.JOIN_GAME);
    request.userId = userId;
    request.gameId = gameId;
    return request;
  }

}
