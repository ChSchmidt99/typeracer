package app.model;

import protocol.RaceResult;

/** Observer for MultiplayerModel. */
public interface MultiplayerModelObserver {

  void raceStarted();

  void updatedRaceState();

  void checkeredFlag(long raceEndTimestamp);

  void updatedTimer(long time);

  void updatedCountDown(long time);

  void receivedRaceResult(RaceResult result);

  void receivedError(String message);
}
