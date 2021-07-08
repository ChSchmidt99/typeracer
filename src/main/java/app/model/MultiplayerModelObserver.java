package app.model;

import protocol.RaceResult;

/** Observer for MultiplayerModel. */
public interface MultiplayerModelObserver {

  void updatedRaceState();

  void checkeredFlag(long raceEndTimestamp);

  void updatedTimer(long time);

  void receivedRaceResult(RaceResult result);
}
