package backend;

/**
 * Provides an interface for all Backend operations.
 */
public interface Api {

  void registerPlayer(String connectionId, String name);

  void createNewLobby(String connectionId, String userId);

  void joinLobby(String lobbyId, String connectionId, String userId);

  void leaveLobby(String connectionId);

  void startRace(String connectionId);

  void getLobbies(String connectionId);

}
