package app;

import client.Client;
import client.ClientImpl;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/** Singleton holding the applications state. */
public class ApplicationState {

  private static ApplicationState instance;

  private String userId;
  private Client client;
  private final List<Closeable> closeables;

  private ApplicationState() {
    closeables = new ArrayList<>();
  }

  /**
   * Creates new instance or returns existing one.
   *
   * @return Singleton ApplicationState
   */
  public static ApplicationState getInstance() {
    if (ApplicationState.instance == null) {
      instance = new ApplicationState();
    }
    return instance;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  public void addCloseable(Closeable closeable) {
    closeables.add(closeable);
  }

  public void removeCloseable(Closeable closeable) {
    closeables.remove(closeable);
  }

  public void close() {
    for (Closeable closeable: closeables) {
      try {
        closeable.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Get client or create a new one, if non exists.
   *
   * @return {@link Client}
   */
  public Client getClient() {
    if (client == null) {
      // TODO: Add address/port to config
      try {
        client = new ClientImpl(InetAddress.getByName("127.0.0.1"), 8080);
        closeables.add(client);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return client;
  }
}
