package server;

/**
 * Server main, run Server.
 */
public class Main {
  // TODO: Maybe add those parameters to a server config file
  private static final int MAX_CLIENTS = 25;
  private static final int PORT = 8080;

  /**
   * Run Server.
   *
   * @param args no arguments used
   */
  public static void main(String[] args) {
    Server server = new Server(MAX_CLIENTS);
    try {
      server.run(PORT);
    } catch (Exception exp) {
      System.out.println(exp.getMessage());
    }
  }

}
