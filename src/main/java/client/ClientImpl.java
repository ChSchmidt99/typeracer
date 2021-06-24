 package client;

 import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import java.io.Closeable;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.nio.charset.StandardCharsets;
 import protocol.Request;
 import protocol.RequestFactory;

/ **
 * Client provides all communication with the server.
 */
 public class ClientImpl implements Closeable, Client {

  private final Socket socket;
  private final PrintWriter writer;
  private final Gson gson;
  private final ResponseHandler responseHandler;

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
    GsonBuilder builder = new GsonBuilder();
    this.gson = builder.create();
    this.responseHandler = new ResponseHandler(socket, gson);
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

  @Override
  public void subscribe(ClientObserver observer) {
    responseHandler.subscribe(observer);
  }

  @Override
  public void unsubscribe(ClientObserver observer) {
    responseHandler.unsubscribe(observer);
  }

  private void writeToServer(String message) {
    this.writer.println(message);
  }

 }
