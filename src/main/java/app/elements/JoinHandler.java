package app.elements;

/** Implement to receive notification when Join Button in Lobby Cell was pressed. */
public interface JoinHandler {
  void clickedJoin(String lobbyId);
}
