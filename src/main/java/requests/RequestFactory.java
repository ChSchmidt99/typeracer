package requests;

public class RequestFactory {

    public static Request makeRegisterRequest(String playerName) {
        Request request = new Request(RequestTypes.REGISTER);
        request.playerName = playerName;
        return request;
    }

    public static Request makeNewGameRequest(String userId) {
        Request request = new Request(RequestTypes.NEW_GAME);
        request.userId = userId;
        return request;
    }

    public static Request makeJoinGameRequest(String userId, String gameId) {
        Request request = new Request(RequestTypes.JOIN_GAME);
        request.userId = userId;
        request.gameId = gameId;
        return request;
    }

}
