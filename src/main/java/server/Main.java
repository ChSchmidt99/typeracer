package server;

import database.Database;
import database.DatabaseImpl;

/**
 * Server main, run Server.
 */
// TODO: Rename Main
public class Main {
  // TODO: Maybe add parameters to a server config file or read options
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
      System.out.println(exp.getMessage());
    }
  }

}
