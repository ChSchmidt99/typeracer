package protocol;

/** Factory for creating {@link Request} instances. */
public class RequestFactory {

  /**
   * Make a Register {@link Request} used to register a new player.
   *
   * @param playerName name of the player
   * @return {@link Request}
   */
  public static Request makeRegisterRequest(String playerName, String iconId) {
    Request request = new Request(Request.Types.REGISTER);
    request.playerName = playerName;
    request.iconId = iconId;
    return request;
  }

  /**
   * Make a New game {@link Request} used to create a new game.
   *
   * @param lobbyName name of lobby
   * @return {@link Request}
   */
  public static Request makeNewLobbyRequest(String lobbyName) {
    Request request = new Request(Request.Types.NEW_LOBBY);
    request.lobbyName = lobbyName;
    return request;
  }

  /**
   * Make a join game {@link Request} used to join existing games.
   *
   * @param lobbyId of lobby
   * @return {@link Request}
   */
  public static Request makeJoinLobbyRequest(String lobbyId) {
    Request request = new Request(Request.Types.JOIN_LOBBY);
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

  public static Request makePreviousRaceResultRequest() {
    return new Request(Request.Types.PREV_RACE_RESULT);
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

  /**
   * Make progress update {@link Request} used to send current progress to server.
   *
   * @param snapshot current race progress snapshot
   * @return {@link Request}
   */
  public static Request makeProgressUpdateRequest(ProgressSnapshot snapshot) {
    Request request = new Request(Request.Types.UPDATE_PROGRESS);
    request.snapshot = snapshot;
    return request;
  }

  /**
   * Make progress update {@link Request} used to send current progress to server.
   *
   * @return {@link Request}
   */
  public static Request makeLobbyUpdateRequest() {
    return new Request(Request.Types.LOBBY_UPDATE);
  }

  /**
   * Make chat request used to send chat messages.
   *
   * @return {@link Request}
   */
  public static Request makeChatRequest(String message) {
    Request request = new Request(Request.Types.CHAT_MESSAGE);
    request.message = message;
    return request;
  }

  /**
   * Make chat history request used to request chat history,
   *
   * @return {@link Request}
   */
  public static Request makeChatHistoryRequest() {
    return new Request(Request.Types.CHAT_HISTORY);
  }
}
