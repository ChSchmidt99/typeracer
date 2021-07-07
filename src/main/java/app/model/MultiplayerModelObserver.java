package app.model;

/** Observer for MultiplayerModel. */
public interface MultiplayerModelObserver {

  void updatedRaceState();

  void checkeredFlag(long raceEndTimestamp);

  void updatedTimer(long time);
}
