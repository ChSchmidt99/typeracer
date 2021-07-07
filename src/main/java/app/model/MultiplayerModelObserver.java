package app.model;

public interface MultiplayerModelObserver {

  void updatedRaceState();

  void checkeredFlag(long raceEndTimestamp);
}
