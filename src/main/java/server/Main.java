package server;

/** Server main, run Server. */
public class Main {
  // TODO: Maybe add parameters to a server config file or read options
  private static final int PORT = 8080;

  /**
   * Run Server.
   *
   * @param args no arguments used
   */
  public static void main(String[] args) {
    Server server = new Server();
    try {
      server.run(PORT);
    } catch (Exception exp) {
      System.out.println(exp.getMessage());
    }
  }
}
