// package server;
//
// import backend.Api;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.JsonSyntaxException;
// import java.io.BufferedReader;
// import java.io.Closeable;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.PrintWriter;
// import java.net.Socket;
// import java.nio.charset.StandardCharsets;
// import java.util.UUID;
// import protocol.Request;
// import protocol.RequestTypes;
// import protocol.Response;
// import protocol.ResponseFactory;
//
/// **
// * Representing a Connection to a Client.
// */
// class Connection implements Closeable {
//
//  private final Socket socket;
//  private final BufferedReader reader;
//  private final PrintWriter writer;
//  private final Gson gson;
//  private final String id;
//  private final Api api;
//
//  /**
//   * Constructor of Connection.
//   *
//   * @param socket the Request handler is supposed to run on
//   * @throws IOException if socket is not connected
//   */
//  Connection(Socket socket, Api api) throws IOException {
//    this.socket = socket;
//    this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
//            StandardCharsets.UTF_8));
//    this.writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
//    GsonBuilder builder = new GsonBuilder();
//    this.gson = builder.create();
//    this.api = api;
//    this.id = UUID.randomUUID().toString();
//  }
//
//  /**
//   * Closes socket specified when creating Connection.
//   */
//  @Override
//  public void close() {
//    try {
//      socket.close();
//      reader.close();
//    } catch (IOException e) {
//      Logger.logError(e.getMessage());
//    }
//  }
//
//  /**
//   * Listens on socket and forwards all received messages.
//   */
//  void handleRequests(OnDisconnect onDisconnect) {
//    try {
//      String line;
//      while ((line = this.reader.readLine()) != null) {
//        Request request = gson.fromJson(line, Request.class);
//        receivedRequest(request);
//      }
//    } catch (IOException e) {
//      onDisconnect.closedConnection(this);
//    } catch (JsonSyntaxException e) {
//      sendResponse(ResponseFactory.makeErrorResponse("Invalid Request"));
//    }
//  }
//
//  /**
//   * Send a {@link Response} to the Connection.
//   *
//   * @param response some Response
//   */
//  void sendResponse(Response response) {
//    writer.println(gson.toJson(response));
//  }
//
//  String getId() {
//    return id;
//  }
//
//  private void receivedRequest(Request request) {
//    switch (request.type) {
//      case RequestTypes.REGISTER:
//        api.registerPlayer(id, request.playerName);
//        break;
//      case RequestTypes.NEW_GAME:
//        api.createNewGame(id, request.userId);
//        break;
//      case RequestTypes.JOIN_GAME:
//        api.joinGame(id, request.userId, request.gameId);
//        break;
//      default:
//        Logger.logError("Unknown Request type: " + request.type);
//    }
//  }
// }
