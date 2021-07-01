package util;

/** Contains static method for getting unix epoch timestamp. */
public class Timestamp {

  /**
   * Get unix epoch timestamp in seconds.
   *
   * @return unix epoch time in seconds as long
   */
  public static long currentTimestamp() {
    return System.currentTimeMillis() / 1000L;
  }
}
