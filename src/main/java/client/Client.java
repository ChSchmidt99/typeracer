package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import requests.SampleRequest;

/**
 * Client provides all communication with the server.
 */
public class Client implements Closeable {

  private final Socket socket;
  private final PrintWriter writer;
  private final Gson gson;

  /**
   * Tries to connect to the server and starts listening for responses when successful.
   * Subscribe observer to receive responses.
   *
   * @param serverAddress address to server
   * @param port          port the server listens to
   * @throws IOException if connection attempt fails
   */
  public Client(InetAddress serverAddress, int port) throws IOException {
    this.socket = new Socket(serverAddress, port);
    this.writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

    GsonBuilder builder = new GsonBuilder();
    this.gson = builder.create();
  }

  @Override
  public void close() throws IOException {
    writer.close();
    socket.close();
  }

  void sendSampleRequest() {
    SampleRequest request = new SampleRequest(1, "Hello World");
    String json = gson.toJson(request);
    writeToServer(json);
  }

  private void writeToServer(String message) {
    this.writer.println(message);
  }

}
