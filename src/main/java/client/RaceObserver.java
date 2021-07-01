package client;

import java.util.List;
import protocol.PlayerUpdate;

/** Implement to receive updates in the current race. */
public interface RaceObserver {

  /**
   * Called when the progress of players was updated.
   *
   * @param updates updated players
   */
  void receivedRaceUpdate(List<PlayerUpdate> updates);

  /**
   * Called after first player crossed finish line. No more updates will be accepted by server after
   * raceStop time.
   *
   * @param raceStop unix epoch timestamp of race end in seconds
   */
  void receivedCheckeredFlag(long raceStop);
}
