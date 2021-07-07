package app.model;

import protocol.RaceModel;

public interface GameLobbyModelObserver {

  void updatedLobby();

  void startedRace(RaceModel model);
}
