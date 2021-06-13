package backend;

import protocol.Response;
import protocol.ResponseFactory;
import server.Logger;
import server.PushService;

/**
 * Currently just an experimental Api implementation.
 */
public class ApiImpl implements Api {

    private final PushService pushService;

    public ApiImpl(PushService pushService) {
        this.pushService = pushService;
    }

    @Override
    public void registerPlayer(String connectionId, String name) {
        System.out.println("Called register player with: " + name);
        Response response = ResponseFactory.makeErrorResponse("Not implemented");
        try {
            pushService.sendResponse(connectionId, response);
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }

    @Override
    public void createNewGame(String connectionId, String userId) {
        System.out.println("Called create game with userId: " + userId);
        Response response = ResponseFactory.makeErrorResponse("Not implemented");
        try {
            pushService.sendResponse(connectionId, response);
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }

    @Override
    public void joinGame(String connectionId, String userId, String gameId) {
        System.out.println("Called join game with: " + gameId);
        Response response = ResponseFactory.makeErrorResponse("Not implemented");
        try{
            pushService.sendResponse(connectionId, response);
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }

}
