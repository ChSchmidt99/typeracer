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
    Request request = new Request(Request.Types.REGISTER);
    request.playerName = playerName;
    return request;
  }

  /**
   * Make a New game {@link Request} used to create a new game.
   *
   * @param userId of initial player
   * @return {@link Request}
   */
  public static Request makeNewLobbyRequest(String userId) {
    Request request = new Request(Request.Types.NEW_LOBBY);
    request.userId = userId;
    return request;
  }

  /**
   * Make a join game {@link Request} used to join existing games.
   *
   * @param userId of player
   * @param lobbyId of lobby
   * @return {@link Request}
   */
  public static Request makeJoinLobbyRequest(String userId, String lobbyId) {
    Request request = new Request(Request.Types.JOIN_LOBBY);
    request.userId = userId;
    request.lobbyId = lobbyId;
    return request;
  }

  public static Request makeStartRaceRequest() {
    return new Request(Request.Types.START_RACE);
  }

  public static Request makeLeaveLobbyRequest() {
    return new Request(Request.Types.LEAVE_LOBBY);
  }

  public static Request makeGetOpenLobbiesRequest() {
    return new Request(Request.Types.GET_LOBBIES);
  }

  /**
   * Make a is ready {@link Request} used to set player to be ready or not.
   *
   * @param isReady whether or not user is ready
   * @return {@link Request}
   */
  public static Request makeIsReadyRequest(boolean isReady) {
    Request request = new Request(Request.Types.PLAYER_READY);
    request.isReady = isReady;
    return request;
  }

}
