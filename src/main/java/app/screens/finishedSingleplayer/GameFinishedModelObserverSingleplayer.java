package app.screens.finishedSingleplayer;

import protocol.LobbyData;

/** Observer for GameFinishedModel. */
interface GameFinishedModelObserverSingleplayer {
  void receivedError(String message);
}
