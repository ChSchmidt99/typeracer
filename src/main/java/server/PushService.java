package server;

import protocol.Response;

import java.util.Set;

/**
 * Service used to send responses to clients.
 */
public interface PushService {

  /**
   * Send a {@link Response} to the specified Connection.
   *
   * @param connectionId of some existing {@link Connection}
   * @param response some {@link Response}
   * @throws Exception when no Connection with connectionId exists
   */
  void sendResponse(String connectionId, Response response);

  /**
   * Send a {@link Response} to all specified Connections.
   *
   * @param connectionIds Set of connections
   * @param response some {@link Response}
   */
  void sendResponse(Set<String> connectionIds, Response response);

}
