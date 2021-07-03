package util;

import java.util.Date;

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

  /**
   * Parse unix timestamp to {@link Date}.
   *
   * @param timestamp unix epoch time in seconds as long
   * @return date
   */
  public static Date timestampToDate(long timestamp) {
    long millis = timestamp * 1000L;
    return new Date(millis);
  }
}
