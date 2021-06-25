package util;

public class Timestamp {

  public static long currentTimestamp() {
    return System.currentTimeMillis() / 1000L;
  }
}
