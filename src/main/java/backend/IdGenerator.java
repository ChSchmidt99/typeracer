package backend;

/**
 * Used to get consecutive IDs.
 */
class IdGenerator {

  private int current;

  /**
   * Id generator starting at startId.
   * @param startId first ID
   */
  IdGenerator(int startId) {
    this.current = startId;
  }

  /**
   * Returns a unique ID and increments the counter.
   * @return current ID
   */
  String getId() {
    String id = Integer.toString(current);
    current++;
    return id;
  }
}
