package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import requests.Request;
import requests.RequestInterfaceAdapter;

/**
 * Representing a Connection to a Client.
 */
public class Connection implements Closeable {

  private final Socket socket;
  private final BufferedReader reader;
  private final Gson gson;

  /**
   * Constructor of RequestHandler.
   *
   * @param socket the Request handler is supposed to run on
   * @throws IOException if socket is not connected
   */
  public Connection(Socket socket) throws IOException {
    this.socket = socket;
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
            StandardCharsets.UTF_8));
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Request.class, new RequestInterfaceAdapter());
    this.gson = builder.create();
  }

  /**
   * Closes socket specified when creating RequestHandler.
   */
  @Override
  public void close() {
    try {
      socket.close();
      reader.close();
    } catch (IOException e) {
      Logger.log(Logger.LogLevel.ERROR, e.getMessage());
    }
  }

  /**
   * Listens on socket and forwards all received messages.
   */
  public void handleRequests() {
    try {
      String line;
      while ((line = this.reader.readLine()) != null) {
        receivedRequest(line);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void receivedRequest(String message) {
    System.out.println(message);
    // TODO: Handle invalid requests
    Request request = gson.fromJson(message, Request.class);
    request.execute();
  }
}
