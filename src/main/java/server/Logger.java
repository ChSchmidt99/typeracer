package server;

/**
 * Use as static class to log information with a corresponding log level.
 */
public abstract class Logger {

  /**
   * Use LogLevel to change the amount of information that is logged.
   */
  enum LogLevel {
    /**
     * Includes INFO, ERROR and additional DEBUG information.
     */
    DEBUG,

    /**
     * Includes ERROR and INFO information.
     */
    INFO,

    /**
     * Just outputs Errors.
     */
    ERROR,

    /**
     * Log nothing.
     */
    OFF
  }

  /**
   * Set how much information is logged.
   */
  static void setLevel(LogLevel level) {
    Logger.level = level;
  }

  private static LogLevel level = LogLevel.INFO;

  public static void logInfo(String message) {
    log(LogLevel.INFO, message);
  }

  public static void logDebug(String message) {
    log(LogLevel.DEBUG, message);
  }

  public static void logError(String message) {
    log(LogLevel.ERROR, message);
  }


  /**
   * Log a message to standard out, it will only be displayed, if it has the appropriate level.
   */
  static void log(LogLevel level, String message) {
    switch (Logger.level) {
      case OFF:
        return;
      case DEBUG:
        System.out.println(level + ": " + message);
        break;
      case INFO:
        if (level != LogLevel.DEBUG) {
          System.out.println(level + ": " + message);
        }
        break;
      case ERROR:
        if (level == LogLevel.ERROR) {
          System.out.println(level + ": " + message);
        }
        break;
      default:
        break;
    }
  }
}
