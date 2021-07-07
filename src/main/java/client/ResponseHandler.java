package client;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import protocol.Response;

class ResponseHandler implements Closeable {

  private final BufferedReader reader;
  private final Gson gson;
  private final ExecutorService executorService;
  private final HashSet<ClientObserver> observers;
  private final HashSet<RaceObserver> raceObservers;
  private final HashSet<RaceResultObserver> resultObservers;
  private final HashSet<LobbyObserver> lobbyObservers;
  private final HashSet<ErrorObserver> errorObservers;

  ResponseHandler(Socket socket, Gson gson) throws IOException {
    this.reader =
        new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
    this.gson = gson;
    this.observers = new HashSet<>();
    this.lobbyObservers = new HashSet<>();
    this.raceObservers = new HashSet<>();
    this.resultObservers = new HashSet<>();
    this.errorObservers = new HashSet<>();
    executorService = Executors.newFixedThreadPool(1);
    executorService.execute(this::startListening);
  }

  /**
   * Closes reader and executor service.
   *
   * @throws IOException when reader can't be closed
   */
  @Override
  public void close() throws IOException {
    this.executorService.shutdownNow();
    this.reader.close();
  }

  void subscribe(ClientObserver observer) {
    observers.add(observer);
  }

  void unsubscribe(ClientObserver observer) {
    observers.remove(observer);
  }

  void subscribeRaceUpdates(RaceObserver observer) {
    raceObservers.add(observer);
  }

  void unsubscribeRaceUpdates(RaceObserver observer) {
    raceObservers.remove(observer);
  }

  void subscribeLobbyUpdates(LobbyObserver observer) {
    lobbyObservers.add(observer);
  }

  void unsubscribeLobbyUpdates(LobbyObserver observer) {
    lobbyObservers.remove(observer);
  }

  void subscribeResults(RaceResultObserver observer) { resultObservers.add(observer); }

  void unsubscribeResults(RaceResultObserver observer) { resultObservers.remove(observer); }

  void subscribeErrors(ErrorObserver observer) {
    errorObservers.add(observer);
  }

  void unsubscribeErrors(ErrorObserver observer) {
    errorObservers.remove(observer);
  }

  private void startListening() {
    try {
      String line;
      while ((line = this.reader.readLine()) != null) {
        Response response = gson.fromJson(line, Response.class);
        receivedResponse(response);
      }
    } catch (IOException e) {
      // TODO: Handle lost Server connection
      System.out.println(e.getMessage());
    }
  }

  private void receivedResponse(Response response) {
    switch (response.type) {
      case Response.Types.REGISTERED:
        observers.forEach(
            (observer) -> {
              observer.registered(response.userId);
            });
        break;
      case Response.Types.ERROR:
        errorObservers.forEach(
            (observer) -> {
              observer.receivedError("SERVER ERROR: " + response.message);
            });
        break;
      case Response.Types.GAME_STARTING:
        lobbyObservers.forEach(
            (observer) -> {
              observer.gameStarting(response.race);
            });
        break;
      case Response.Types.LOBBY_UPDATE:
        lobbyObservers.forEach(
            (observer) -> {
              observer.receivedLobbyUpdate(response.lobby);
            });
        break;
      case Response.Types.OPEN_LOBBIES:
        observers.forEach(
            (observer) -> {
              observer.receivedOpenLobbies(response.lobbies);
            });
        break;
      case Response.Types.RACE_UPDATE:
        raceObservers.forEach(
            (observer) -> {
              observer.receivedRaceUpdate(response.playerUpdates);
            });
        break;
      case Response.Types.CHECKERED_FLAG:
        raceObservers.forEach(
            (observer) -> {
              observer.receivedCheckeredFlag(response.raceStop);
            });
        break;
      case Response.Types.RACE_RESULT:
        resultObservers.forEach(
            (observer) -> {
              observer.receivedRaceResult(response.raceResult);
            });
        break;
      default:
        System.out.println("Received unknown ResponseType: " + response.type);
    }
  }
}
