package requests;

// TODO: execute should only be available in server, find a solution (maybe a wrapper for server)

/**
 * Interface to be implemented by all Requests.
 */
public interface Request {
  void execute();
}
