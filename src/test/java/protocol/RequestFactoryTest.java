package protocol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/** Request Factory Tests. */
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
    String lobbyName = "Some name";
    String iconId = "Some Icon Id";
    Request request = RequestFactory.makeNewLobbyRequest(userId, lobbyName, iconId);
    String expected =
        String.format(
            "{\"type\":\"new lobby\",\"userId\":\"%s\",\"iconId\":\"%s\",\"lobbyName\":\"%s\"}",
            userId, iconId, lobbyName);
    String result = gson.toJson(request);
    assertEquals(expected, result);
  }

  @Test
  void join_game_request() {
    String userId = UUID.randomUUID().toString();
    String gameId = UUID.randomUUID().toString();
    String iconId = UUID.randomUUID().toString();
    String expected =
        String.format(
            "{\"type\":\"join lobby\",\"userId\":\"%s\",\"iconId\":\"%s\",\"lobbyId\":\"%s\"}",
            userId, iconId, gameId);
    Request request = RequestFactory.makeJoinLobbyRequest(userId, gameId, iconId);
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
