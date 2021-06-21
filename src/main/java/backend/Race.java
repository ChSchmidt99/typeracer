package backend;

import database.Database;
import protocol.RaceModel;

import java.util.List;

class Race {

  private final String textToType;
  private final List<String> players;
  private boolean isRunning;

  Race(Database database, List<String> players) {
    this.textToType = database.getTextToType();
    this.players = players;
    this.isRunning = true;
  }

  RaceModel getModel() {
    return new RaceModel(this.textToType, this.players);
  }

  boolean getIsRunning() {
    return isRunning;
  }

}
