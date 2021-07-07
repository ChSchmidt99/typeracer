package client;

import protocol.RaceResult;

public interface RaceResultObserver {

  void receivedRaceResult(RaceResult result);

}
