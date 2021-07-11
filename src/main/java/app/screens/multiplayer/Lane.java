package app.screens.multiplayer;

import app.custom.RaceTrack;
import javafx.scene.control.Label;

class Lane {

  private final Label wpmLabel;
  private final RaceTrack track;

  Lane(Label wpmLabel, RaceTrack track) {
    this.wpmLabel = wpmLabel;
    this.track = track;
  }

  RaceTrack getTrack() {
    return track;
  }

  Label getWordsPerMinuteLabel() {
    return wpmLabel;
  }
}
