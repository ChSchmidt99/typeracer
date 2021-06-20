package server;

import java.util.HashMap;
import java.util.Set;
import protocol.Response;

class PushServiceImpl implements PushService {

  private final HashMap<String, Connection> connections;

  PushServiceImpl() {
    this.connections = new HashMap<>();
  }

  void addConnection(Connection connection) {
    connections.put(connection.getId(), connection);
  }

  void removeConnection(Connection connection) {
    connections.remove(connection.getId());
  }

  @Override
  public void sendResponse(String connectionId, Response response) {
    try {
      respond(connectionId, response);
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  @Override
  public void sendResponse(Set<String> connectionIds, Response response) {
    for (String connectionId : connectionIds) {
      try {
        sendResponse(connectionId, response);
      } catch (Exception e) {
        Logger.logError(e.getMessage());
      }
    }
  }

  // TODO: Maybe send all responses in new Threads to avoid blocking
  private void respond(String connectionId, Response response) throws Exception {
    Connection connection = connections.get(connectionId);
    if (!connections.containsKey(connectionId)) {
      throw new Exception("connection with id " + connectionId + " does not exist");
    }
    connection.sendResponse(response);
  }

}
