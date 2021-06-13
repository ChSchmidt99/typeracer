package protocol;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Response Factory Tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResponseFactoryTest {

  private Gson gson;

  @BeforeAll
  void init() {
    GsonBuilder builder = new GsonBuilder();
    this.gson = builder.create();
  }

  @Test
  void new_game_request() {
    String message = "Some message";
    Response response = ResponseFactory.makeErrorResponse(message);
    String expected = String.format("{\"type\":\"error\",\"message\":\"%s\"}", message);
    String result = gson.toJson(response);
    assertEquals(expected, result);
  }

}
