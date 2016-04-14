package ua.in.iua.iuadebugtools;

import android.util.Log;

import com.google.common.collect.EvictingQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
        switch (logMessage.mLogType) {
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

    /***
     * Log message level.
     */
    public enum LogType {
        INFO,
        WARNING,
        DEBUG,
        ERROR,
        VERBOSE
    }

    /***
     * Class represents log message record.
     */
    public class LogMessage {
        /***
         * Class name of sender.
         */
        private final String mLoggedClassName;
        /***
         * Log type {@link LogType LogType}.
         */
        private final LogType mLogType;
        /***
         * Message.
         */
        private final String mMessage;
        /***
         * Log record date/time.
         */
        private final Date mDate;

        public LogMessage(Object object, LogType logType, String message) {
            mLoggedClassName = object.getClass().getName();
            mLogType = logType;
            mMessage = message;
            mDate = new Date();
        }

        @Override
        public String toString() {
            return "LogMessage{" +
                    "loggedClassName='" + mLoggedClassName + '\'' +
                    ", logType=" + mLogType +
                    ", message='" + mMessage + '\'' +
                    ", date=" + mDate +
                    '}';
        }

        public String getLoggedClassName() {
            return mLoggedClassName;
        }

        public Date getDate() {
            return mDate;
        }

        public LogType getLogType() {
            return mLogType;
        }

        public String getMessage() {
            return mMessage;
        }
    }
}
