package ua.in.iua.iuadebugtools;

import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * mipoint
 * Created by yeromchenko on 18.06.16
 */

public interface Logger {

    void addLogListener(LogListener logListener);

    void removeLogListener(LogListener logListener);

    /**
     * Log a verbose message with optional format args.
     */
    void v(Object where, @NonNull String message, Object... args);

    /**
     * Log a verbose exception and a message with optional format args.
     */
    void v(Object where, Throwable t, @NonNull String message, Object... args);

    /**
     * Log a debug message with optional format args.
     */
    void d(Object where, @NonNull String message, Object... args);

    /**
     * Log a debug exception and a message with optional format args.
     */
    void d(Object where, Throwable t, @NonNull String message, Object... args);

    /**
     * Log an info message with optional format args.
     */
    void i(Object where, @NonNull String message, Object... args);

    /**
     * Log an info exception and a message with optional format args.
     */
    void i(Object where, Throwable t, @NonNull String message, Object... args);

    /**
     * Log a warning message with optional format args.
     */
    void w(Object where, @NonNull String message, Object... args);

    /**
     * Log a warning exception and a message with optional format args.
     */
    void w(Object where, Throwable t, @NonNull String message, Object... args);

    /**
     * Log an error message with optional format args.
     */
    void e(Object where, @NonNull String message, Object... args);

    /**
     * Log an error exception and a message with optional format args.
     */
    void e(Object where, Throwable t, @NonNull String message, Object... args);

    /**
     * Returns last log messages
     * @param limit result collection size limit
     * @return collection of last log messages{@link LogMessage} if supports or empty collection.
     */
    Collection<LogMessage> getMessages(int limit);

    /***
     * Returns last log messages with default limit
     * @return collection of last log messages{@link LogMessage} if supports or empty collection.
     */
    Collection<LogMessage> getMessages();
}
