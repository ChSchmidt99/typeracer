package app.screens.singleplayer;

import app.screens.singleplayer.FinishedMessage;
import protocol.RaceResult;

public interface SingleplayerModelObserver {

  void raceStarted();

  void updatedRaceState();

  void checkeredFlag(long raceEndTimestamp);

  void updatedTimer(long time);

  void updatedCountDown(long time);

  void receivedRaceResult(RaceResult result);

  void changedFinishedMessage(FinishedMessage message);

  void receivedError(String message);

}
