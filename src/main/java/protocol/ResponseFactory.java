package protocol;

import java.util.List;

/** Factory used for creating {@link Response} instances. */
public class ResponseFactory {

  /**
   * Make Error response. Used for any errors encountered.
   *
   * @param message error message
   * @return {@link Response}
   */
  public static Response makeErrorResponse(String message) {
    Response response = new Response(Response.Types.ERROR);
    response.message = message;
    return response;
  }

  /**
   * Make Lobby response. Used for any lobby updates.
   *
   * @param lobby {@link LobbyData} of the updated lobby
   * @return {@link Response}
   */
  public static Response makeLobbyUpdateResponse(LobbyData lobby) {
    Response response = new Response(Response.Types.LOBBY_UPDATE);
    response.lobby = lobby;
    return response;
  }

  /**
   * Make race starting response. Used to notify users when a race is about to start.
   *
   * @param model {@link RaceData} of the started race
   * @return {@link Response}
   */
  public static Response makeRaceStartingResponse(RaceData model) {
    Response response = new Response(Response.Types.GAME_STARTING);
    response.race = model;
    return response;
  }

  /**
   * Make registered response. Used to send userId to newly registered players
   *
   * @param userId id of user
   * @return {@link Response}
   */
  public static Response makeRegisteredResponse(String userId) {
    Response response = new Response(Response.Types.REGISTERED);
    response.userId = userId;
    return response;
  }

  /**
   * Make Lobbies response. Used to send all open lobbies to clients.
   *
   * @param lobbies list of {@link LobbyData}
   * @return {@link Response}
   */
  public static Response makeLobbiesResponse(List<LobbyData> lobbies) {
    Response response = new Response(Response.Types.OPEN_LOBBIES);
    response.lobbies = lobbies;
    return response;
  }

  /**
   * Make Player updates response. Used to send game updates to all clients.
   *
   * @param updates all updated players
   * @return {@link Response}
   */
  public static Response makeRaceUpdatesResponse(List<PlayerUpdate> updates) {
    Response response = new Response(Response.Types.RACE_UPDATE);
    response.playerUpdates = updates;
    return response;
  }

  /**
   * Make checkered flag response. Used to send raceStop time to all clients after first player
   * finished.
   *
   * @param raceStop unix epoch time of race end in seconds
   * @return {@link Response}
   */
  public static Response makeCheckeredFlagResponse(long raceStop) {
    Response response = new Response(Response.Types.CHECKERED_FLAG);
    response.raceStop = raceStop;
    return response;
  }

  /**
   * Make race result response used to send race results to clients.
   *
   * @param result {@link RaceResult}
   * @return {@link Response}
   */
  public static Response makeRaceResultResponse(RaceResult result) {
    Response response = new Response(Response.Types.RACE_RESULT);
    response.raceResult = result;
    return response;
  }

  /**
   * Make race result response used to send race results to clients.
   *
   * @param chatHistory history of all chats
   * @return {@link Response}
   */
  public static Response makeChatResponse(List<ChatMessageData> chatHistory) {
    Response response = new Response(Response.Types.CHAT_HISTORY);
    response.chatHistory = chatHistory;
    return response;
  }
}
