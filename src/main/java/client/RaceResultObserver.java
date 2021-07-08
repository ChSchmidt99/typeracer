package client;

import protocol.RaceResult;

/** Use to receive race results. */
public interface RaceResultObserver {

  void receivedRaceResult(RaceResult result);
}
