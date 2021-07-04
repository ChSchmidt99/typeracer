package server;

import backend.Api;
import backend.ApiImpl;
import database.Database;
import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.Logger;

/** Main instance for Server. */
public class Server implements Closeable, OnDisconnect {

  private ServerSocket socket;
  private final Set<Closeable> closeables;
  private final ExecutorService executorService;
  private boolean isRunning;
  private final Api api;
  private final PushServiceImpl pushService;

  /**
   * Constructor of Server.
   *
   * @param database database
   */
  public Server(Database database) {
    isRunning = false;
    closeables = new HashSet<>();
    executorService = Executors.newCachedThreadPool();
    this.pushService = new PushServiceImpl();
    this.api = new ApiImpl(pushService, database);
  }

  /**
   * Closes all connections and then the server itself.
   *
   * @throws IOException when something can't be closed
   */
  @Override
  public void close() throws IOException {
    isRunning = false;
    for (Closeable closeable : closeables) {
      closeable.close();
    }
    executorService.shutdownNow();
    socket.close();
  }

  /**
   * Run the server on the specified port.
   *
   * @param port the port to run the server on
   * @throws IOException when server cannot be started
   */
  public void run(int port) throws IOException {
    socket = new ServerSocket(port);
    isRunning = true;
    awaitConnections();
  }

  /**
   * Callback when connection to a client is lost.
   *
   * @param connection the disconnected {@link Connection}
   */
  @Override
  public void closedConnection(Connection connection) {
    Logger.logInfo("Connection " + connection.getId() + " disconnected");
    closeables.remove(connection);
    connection.close();
    pushService.removeConnection(connection);
  }

  private void awaitConnections() throws IOException {
    while (isRunning) {
      Logger.logInfo("Awaiting Connections...");
      Socket clientSocket = socket.accept();
      if (clientSocket != null) {
        Logger.logInfo("Accepted Connection, Socket: " + clientSocket);
        Connection connection = new Connection(clientSocket, this.api);
        pushService.addConnection(connection);
        closeables.add(connection);
        executorService.execute(
            () -> {
              connection.handleRequests(this);
            });
      }
    }
  }
}
