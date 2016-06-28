package ua.in.iua.iuadebugtools;

import android.util.Log;

import com.google.common.collect.EvictingQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** *
 * Logger.
 * Created by rusin on 22.12.15.
 */

/***
 * Logger. Collects all log messages in one place for next processing.
 */
public class Logger {
    /***
     * Static instance.
     */
    private static Logger sInstance;
    /***
     * Collection with log messages.
     */
    private final EvictingQueue<LogMessage> mLogMessages = EvictingQueue.create(100);

    private Logger() {

    }

    /***
     * Returns instance of logger.
     *
     * @return Logger instance.
     */
    synchronized public static Logger getInstance() {
        if (sInstance == null) {
            sInstance = new Logger();
        }
        return sInstance;
    }

    /***
     * Returns all logged messages.
     *
     * @return Queue of messages.
     */
    public List<LogMessage> getLogMessages() {
        ArrayList<LogMessage> logMessages = new ArrayList<>(mLogMessages);
        Collections.reverse(logMessages);
        return logMessages;
    }

    /***
     * Writes log message to logger.
     *
     * @param object  Sender of log message.
     * @param type    Type of message {@link LogType LogType}.
     * @param message Message.
     */
    public void log(Object object, LogType type, String message) {
        LogMessage logMessage = new LogMessage(object, type, message);
        mLogMessages.add(logMessage);
        switch (logMessage.getLogType()) {
            case INFO:
                Log.i("LOGGER", logMessage.toString());
                break;
            case WARNING:
                Log.w("LOGGER", logMessage.toString());
                break;
            case DEBUG:
                Log.d("LOGGER", logMessage.toString());
                break;
            case ERROR:
                Log.e("LOGGER", logMessage.toString());
                break;
            case VERBOSE:
                Log.v("LOGGER", logMessage.toString());
                break;
        }
    }
}
