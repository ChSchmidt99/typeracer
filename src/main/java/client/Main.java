package client;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Temporary Client main for testing.
 */
public class Main implements ClientObserver {

  /**
   * Send sample request to server.
   *
   * @param args no args
   */
  public static void main(String[] args) {
    try {
      Main m = new Main();
      InetAddress addr = InetAddress.getByName("127.0.0.1");
      Client client = new ClientImpl(addr, 8080);
      client.subscribe(m);
      client.registerUser("Cooler Mensch");
      client.newGame("some userId");
      client.startGame();
    } catch (IOException e) {
      System.out.print(e.getMessage());
    }
  }

  @Override
  public void receivedError(String message) {
    System.out.println(message);
  }

  @Override
  public void joinedGame(String gameId, boolean isRunning) {
    System.out.println("Joined Game " + gameId);
  }

  @Override
  public void gameStarting(String text) {
    System.out.println("Game starting with text: " + text);
  }

  @Override
  public void playerJoined(String name) {
    System.out.println("Player joined: " + name);
  }

  @Override
  public void playerLeft(String name) {
    System.out.println("Player Left: " + name);
  }
}
