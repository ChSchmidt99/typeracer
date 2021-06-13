package client;

public interface Client {

    void registerUser(String userName);

    void newGame(String userId);

    void joinGame(String userId, String gameId);

    void subscribe(ClientObserver observer);

    void unsubscribe(ClientObserver observer);

}
