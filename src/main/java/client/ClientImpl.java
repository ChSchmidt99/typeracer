package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import protocol.Request;
import protocol.RequestFactory;
import protocol.Response;

/**
 * Client provides all communication with the server.
 */
public class ClientImpl implements Closeable, Client {

  private final Socket socket;
  private final PrintWriter writer;
  private final BufferedReader reader;
  private final Gson gson;
  private final ExecutorService executorService;

  /**
   * Tries to connect to the server and starts listening for responses when successful.
   * Subscribe observer to receive responses.
   *
   * @param serverAddress address to server
   * @param port          port the server listens to
   * @throws IOException if connection attempt fails
   */
  public ClientImpl(InetAddress serverAddress, int port) throws IOException {
    this.socket = new Socket(serverAddress, port);
    this.writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
            StandardCharsets.UTF_8));
    GsonBuilder builder = new GsonBuilder();
    this.gson = builder.create();
    executorService = Executors.newFixedThreadPool(1);
    executorService.execute(this::startListening);
  }

  @Override
  public void close() throws IOException {
    writer.close();
    socket.close();
  }

  @Override
  public void registerUser(String userName) {
    Request request = RequestFactory.makeRegisterRequest(userName);
    writeToServer(gson.toJson(request));
  }

  @Override
  public void newGame(String userId) {
    Request request = RequestFactory.makeNewGameRequest(userId);
    writeToServer(gson.toJson(request));
  }

  @Override
  public void joinGame(String userId, String gameId) {
    Request request = RequestFactory.makeJoinGameRequest(userId, gameId);
    writeToServer(gson.toJson(request));
  }

  private void writeToServer(String message) {
    this.writer.println(message);
  }

  private void startListening() {
    try {
      String line;
      while ((line = this.reader.readLine()) != null) {
        Response response = gson.fromJson(line, Response.class);
        receivedResponse(response);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void receivedResponse(Response response) {
    System.out.println("Received Response: " + response);
  }

}
