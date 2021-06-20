package client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import protocol.LobbyModel;
import protocol.RaceModel;

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
      client.requestLobbies();
      client.newGame("some userId");
      client.requestLobbies();
      client.startGame();
    } catch (IOException e) {
      System.out.print(e.getMessage());
    }
  }

  @Override
  public void registered(String userId) {
    System.out.println("Registered with userId: " + userId);
  }

  @Override
  public void receivedError(String message) {
    System.out.println(message);
  }

  @Override
  public void gameStarting(RaceModel race) {
    System.out.println("Game starting with text: " + race.textToType);
  }

  @Override
  public void receivedLobbyUpdate(LobbyModel lobby) {
    System.out.println("Received Lobby update:");
    System.out.println(lobby.players);
    System.out.println(lobby.id);
    System.out.println(lobby.isRunning);
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    System.out.println("Open lobbies:");
    System.out.println(lobbies);
  }


}
