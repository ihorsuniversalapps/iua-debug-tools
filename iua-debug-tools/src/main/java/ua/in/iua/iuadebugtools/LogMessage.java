package ua.in.iua.iuadebugtools;

import java.util.Date;

/***
 * Class represents log message record.
 */
public class LogMessage {
    private static final String UNKNOWN_CLASS = "unknown_class";
    private static final String EMPTY_MESSAGE = "empty_message";
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
        mLoggedClassName = object != null ? object.getClass().getName() : UNKNOWN_CLASS;
        mLogType = logType != null ? logType : LogType.DEBUG;
        mMessage = message != null ? message : EMPTY_MESSAGE;
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