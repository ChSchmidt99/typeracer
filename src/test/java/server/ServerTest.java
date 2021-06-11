package server;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerTest {

    private final int port = 8080;
    private Server server;

    @BeforeAll
    void startServer() {
        server = new Server();
        Logger.setLevel(Logger.LogLevel.ERROR);
        new Thread(() -> {
            try {
                server.run(port);
            } catch (IOException e) {
                fail(e.getMessage());
            }
        }).start();
    }

    @Test
    void server_accepts_connection() {
        try {
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(serverAddress, port);
            socket.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
        try {
            server.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
