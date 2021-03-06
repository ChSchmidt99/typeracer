package backend;

import protocol.ProgressSnapshot;

/** Provides an interface for all Backend operations. */
public interface Api {

  /**
   * Call to register a new player with the given name.
   *
   * @param connectionId id of the server connection
   * @param name of user
   * @param iconId id of icon
   */
  void registerPlayer(String connectionId, String name, String iconId);

  /**
   * Call to create a new lobby.
   *
   * @param connectionId id of the server connection
   * @param lobbyName name of the lobby
   */
  void createNewLobby(String connectionId, String lobbyName);

  /**
   * Call to join an existing lobby.
   *
   * @param connectionId id of server connection
   * @param lobbyId id of lobby to join
   */
  void joinLobby(String connectionId, String lobbyId);

  /**
   * Call to leave if currently in a lobby.
   *
   * @param connectionId id of the server connection
   */
  void leaveLobby(String connectionId);

  /**
   * Call to start a race with all ready players.
   *
   * @param connectionId id of server connection
   */
  void startRace(String connectionId);

  /**
   * Call to request a list of all open lobbies.
   *
   * @param connectionId id of the server connection
   */
  void getLobbies(String connectionId);

  /**
   * Call to send a lobby update to the specified connection.
   *
   * @param connectionId id of the server connection
   */
  void sendLobbyUpdate(String connectionId);

  /**
   * Call to set whether or not a player is ready.
   *
   * @param connectionId id of the server connection
   * @param isReady whether or not the player is ready
   */
  void setPlayerReady(String connectionId, boolean isReady);

  /**
   * Call to update a players race progress.
   *
   * @param connectionId id of the server connection
   * @param snapshot snapshot of the race progress
   */
  void updateProgress(String connectionId, ProgressSnapshot snapshot);

  void sendPreviousRaceResult(String connectionId);

  void requestChatHistory(String connectionId);

  void sendChat(String connectionId, String message);
}
