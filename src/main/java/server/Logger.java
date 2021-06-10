package server;

/**
 * Use as static class to log information with a corresponding log level.
 */
public abstract class Logger {

    /** Use LogLevel to change the amount of information that is logged. */
    public static enum LogLevel {
        /** Includes INFO, ERROR and additional DEBUG information. */
        DEBUG,
        /** Includes ERROR and INFO information. */
        INFO,
        /** Just outputs Errors. */
        ERROR
    }

    /** Change LEVEL to adjust the information logged. */
    public static final LogLevel level = LogLevel.INFO;

    /** Log a message to standard out, it will only be displayed, if it has the appropriate level. */
    public static void log(LogLevel level, String message) {
        switch (Logger.level) {
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
