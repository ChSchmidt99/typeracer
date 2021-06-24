package protocol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Request Factory Tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestFactoryTest {

  private Gson gson;

  @BeforeAll
  void init() {
    GsonBuilder builder = new GsonBuilder();
    this.gson = builder.create();
  }

  @Test
  void new_game_request() {
    String userId = UUID.randomUUID().toString();
    Request request = RequestFactory.makeNewGameRequest(userId);
    String expected = String.format("{\"type\":\"new game\",\"userId\":\"%s\"}", userId);
    String result = gson.toJson(request);
    assertEquals(expected, result);
  }

  @Test
  void join_game_request() {
    String userId = UUID.randomUUID().toString();
    String gameId = UUID.randomUUID().toString();
    String expected = String.format("{\"type\":\"join game\",
    \"userId\":\"%s\",\"gameId\":\"%s\"}",
            userId, gameId);
    Request request = RequestFactory.makeJoinGameRequest(userId, gameId);
    String result = gson.toJson(request);
    assertEquals(expected, result);
  }

  @Test
  void register_request() {
    String playerName = "Cooler Mensch";
    String expected = String.format("{\"type\":\"register\",\"playerName\":\"%s\"}", playerName);
    Request request = RequestFactory.makeRegisterRequest(playerName);
    String result = gson.toJson(request);
    assertEquals(expected, result);
  }

}
