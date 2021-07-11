package app;

/** Used to store all configuration information for an application. */
public class Config {

  private final String address;
  private final int port;

  /**
   * Create new config.
   *
   * @param address the client connects to
   * @param port the server is running on
   */
  public Config(String address, int port) {
    this.address = address;
    this.port = port;
  }

  public String getAddress() {
    return address;
  }

  public int getPort() {
    return port;
  }
}
