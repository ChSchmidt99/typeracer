package server;

import database.Database;
import database.DatabaseImpl;

/** Entry point for server execution. Change static attributes for configuration. */
public class ServerRunner {

  private static final int PORT = 8080;

  /**
   * Run Server.
   *
   * @param args no arguments used
   */
  public static void main(String[] args) {
    try {
      Database database = new DatabaseImpl();
      Server server = new Server(database);
      server.run(PORT);
    } catch (Exception exp) {
      System.out.println("Unable to start server:");
      System.out.println(exp.getMessage());
    }
  }
}
