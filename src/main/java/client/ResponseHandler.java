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
import protocol.ResponseTypes;

class ResponseHandler implements Closeable {

  private final BufferedReader reader;
  private final Gson gson;
  private final ExecutorService executorService;
  private final HashSet<ClientObserver> observers;

  ResponseHandler(Socket socket, Gson gson) throws IOException {
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
            StandardCharsets.UTF_8));
    this.gson = gson;
    this.observers = new HashSet<>();
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

  private void startListening() {
    try {
      String line;
      while ((line = this.reader.readLine()) != null) {
        System.out.println(line);
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
      case ResponseTypes.ERROR:
        observers.forEach((observer) -> {
          observer.receivedError(response.message);
        });
        break;
      default:
        System.out.println("Received unknown ResponseType: " + response.type);
    }
  }
}
