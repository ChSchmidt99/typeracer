package backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import mocks.MockDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import protocol.LobbyModel;
import protocol.Response;
import server.PushService;

/**
 * Api Factory Tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiTest implements PushService {

  private Api api;

  private List<LobbyModel> lobbyResponse;

  @BeforeAll
  void init() {
    this.api = new ApiImpl(this, new MockDatabase());
  }

  @Test
  void create_new_lobby() {
    String connectionId = "some id";
    String userId = "some user id";
    this.api.getLobbies(connectionId);
    assertEquals(lobbyResponse.size(), 0);
    this.api.createNewLobby(connectionId, userId);
    this.api.getLobbies(connectionId);
    assertEquals(lobbyResponse.size(), 1);
  }

  /**
   * PushService interface implementation. Store response in test class.
   */
  public void sendResponse(String connectionId, Response response) {
    if (response.type == Response.Types.OPEN_LOBBIES) {
      lobbyResponse = response.lobbies;
    }
  }

  public void sendResponse(Set<String> connectionIds, Response response) {}

}
