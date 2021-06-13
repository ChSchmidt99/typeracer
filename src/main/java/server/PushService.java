package server;

import protocol.Response;

public interface PushService {

  /**
   * Send a {@link Response} to the specified Connection.
   * @param connectionId of some existing {@link Connection}
   * @param response some {@link Response}
   * @throws Exception when no Connection with connectionId exists
   */
  void sendResponse(String connectionId, Response response) throws Exception;

}
