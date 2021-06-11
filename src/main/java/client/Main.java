package client;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Temporary Client main for testing.
 */
public class Main {

  /**
   * Send sample request to server.
   *
   * @param args no args
   */
  public static void main(String[] args) {
    try {
      InetAddress addr = InetAddress.getByName("127.0.0.1");
      Client client = new ClientImpl(addr, 8080);
      client.registerUser("Cooler Mensch");
      client.newGame("some userId");
      client.joinGame("some userId", "some gameId");
    } catch (IOException e) {
      System.out.print(e.getMessage());
    }
  }
}
