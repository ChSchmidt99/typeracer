package backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;

/** Tests for Player class. */
public class PlayerTest {

  @Test
  void updateProgress() {
    int wpm = 4;
    int cpm = wpm * 5;
    Player player = new Player("someId", "someConnection", "someName");
    player.updateProgress(new ProgressSnapshot(0, 60, cpm, 0), cpm);
    PlayerUpdate update = player.getUpdate();
    assertEquals(1, update.percentProgress);
    assertEquals(wpm, update.wpm);
    player.updateProgress(new ProgressSnapshot(0, 30, cpm / 2, 0), cpm);
    PlayerUpdate update2 = player.getUpdate();
    assertEquals(wpm, update2.wpm);
    player.updateProgress(new ProgressSnapshot(0, 120, cpm * 2, 0), cpm * 2);
    PlayerUpdate update3 = player.getUpdate();
    assertEquals(wpm, update3.wpm);
  }
}
