package client;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;
import protocol.LobbyModel;
import protocol.PlayerUpdate;
import protocol.ProgressSnapshot;
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
      client.newLobby("some userId");
      client.requestLobbies();
      client.setIsReady(true);
      client.startRace();
      client.sendProgressUpdate(new ProgressSnapshot(0, 30, 5, 0));
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      client.sendProgressUpdate(new ProgressSnapshot(0, 60, 10, 0));
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
    System.out.println("Received Lobby update: " + lobby);
    System.out.println(lobby.isRunning);
    System.out.println(lobby.id);
    System.out.println(lobby.players);
  }

  @Override
  public void receivedOpenLobbies(List<LobbyModel> lobbies) {
    System.out.println("Open lobbies: " + lobbies);
  }

  @Override
  public void receivedRaceUpdate(List<PlayerUpdate> updates) {
    System.out.println("Race update: " + updates);
    System.out.println("Wpm: " + updates.get(0).wpm);
    System.out.println("Wpm: " + updates.get(0).percentProgress);
  }

  @Override
  public void receivedCheckeredFlag(long raceStop) {
    System.out.println("Checkered Flag! Race stopped: " + Instant.ofEpochSecond(raceStop));
  }

  @Override
  public void receivedRaceResult() {
    System.out.println("Received race result");
  }

}
