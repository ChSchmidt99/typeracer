package server;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import mocks.MockDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/** Server tests. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerTest {

  private static final int PORT = 8081;
  private static final int TIMEOUT = 1000;
  private Server server;

  @BeforeAll
  void startServer() {
    server = new Server(new MockDatabase());
    new Thread(
            () -> {
              try {
                server.run(PORT);
              } catch (IOException e) {
                fail(e.getMessage());
              }
            })
        .start();
  }

  @Test
  void server_accepts_connection() {
    try {
      InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
      Socket socket = new Socket(serverAddress, PORT);
      socket.close();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  void server_send_and_receive() {
    try {
      InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
      Socket socket = new Socket(serverAddress, PORT);

      PrintWriter writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
      writer.println("Hello");

      BufferedReader reader =
          new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

      TimerTask timeoutTask =
          new TimerTask() {
            public void run() {
              try {
                if (!socket.isClosed()) {
                  socket.close();
                }
                fail("Timeout");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          };
      Timer t = new Timer();
      t.schedule(timeoutTask, TIMEOUT);
      reader.readLine();
      t.cancel();
      socket.close();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @AfterAll
  void tearDown() {
    try {
      if (server != null) {
        server.close();
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
