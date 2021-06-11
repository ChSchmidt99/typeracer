package backend;

/**
 * Currently just an experimental Api implementation.
 */
public class ApiImpl implements Api {

    @Override
    public String registerPlayer(String name) {
        System.out.println("Called register player with: " + name);
        return "Some User Id";
    }

    @Override
    public void createNewGame(String userId) {
        System.out.println("Called create game with userId: " + userId);
    }

    @Override
    public void joinGame(String userId, String gameId) {
        System.out.println("Called join game with: " + gameId);
    }

}
