package app.model;

import protocol.RaceData;

/** Observer for GameLobbyModel. */
public interface GameLobbyModelObserver {

  void updatedLobby();

  void startedRace(RaceData model);

  void receivedError(String message);
}
