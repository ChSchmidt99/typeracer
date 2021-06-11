package server;

import backend.Api;
import backend.ApiImpl;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main instance for Server.
 */
public class Server implements Closeable {

  private ServerSocket socket;
  private final Set<Closeable> closeables;
  private final ExecutorService executorService;
  private boolean isRunning;
  private final Api api;

  /**
   * Constructor of Server.
   */
  public Server() {
    isRunning = false;
    closeables = new HashSet<>();
    executorService = Executors.newCachedThreadPool();
    this.api = new ApiImpl();
  }

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
   */
  public void run(int port) throws IOException {
    socket = new ServerSocket(port);
    isRunning = true;
    awaitConnections();
  }

  private void awaitConnections() throws IOException {
    while (isRunning) {
      Logger.log(Logger.LogLevel.INFO, "Awaiting Connections...");
      Socket clientSocket = socket.accept();



      if (clientSocket != null) {
        Logger.log(Logger.LogLevel.INFO, "Accepted Connection, Socket: "
                + clientSocket);
        Connection handler = new Connection(clientSocket, this.api);
        closeables.add(handler);
        executorService.execute(handler::handleRequests);
      }
    }
  }
}
