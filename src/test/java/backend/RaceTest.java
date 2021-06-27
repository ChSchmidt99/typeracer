package backend;

import org.junit.jupiter.api.Test;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
import protocol.Response;
import server.PushService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Race class.
 */
public class RaceTest implements PushService {

  @Test
  void finishRace() {
    String connectionId = "some id";
    Player player = new Player("userId", connectionId, "some name");
    Map<String, Player> players = new HashMap<>();
    players.put(connectionId, player);
    String text = "some text";
    long duration = 1;
    RaceSettings settings = new RaceSettings(duration,1);
    Race race = new Race(settings, text, players, this);
    assertTrue(race.getIsRunning());
    race.updateProgress(connectionId, new ProgressSnapshot(0, 5, text.length(), 0));
    try {
      Thread.sleep(duration * 1000 + 100);
      assertFalse(race.getIsRunning());
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }
  }

  @Override
  public void sendResponse(String connectionId, Response response) {

  }

  @Override
  public void sendResponse(Set<String> connectionIds, Response response) {

  }
}
