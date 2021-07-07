package app;

import client.Client;
import client.ClientImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/** Singleton holding the applications state. */
public class ApplicationState {

  private static ApplicationState instance;

  private String userId;
  private Client client;

  private ApplicationState() {}

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

  public void setClient(Client client) {
    this.client = client;
  }

  public Client getClient() {
    if (client == null) {
      // TODO: Add address/port to config
      try {
        client = new ClientImpl(InetAddress.getByName("127.0.0.1"),8080);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return client;
  }
}
