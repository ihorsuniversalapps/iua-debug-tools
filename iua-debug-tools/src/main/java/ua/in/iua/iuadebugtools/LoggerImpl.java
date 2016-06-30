package ua.in.iua.iuadebugtools;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.collect.EvictingQueue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * mipoint
 * Created by yeromchenko on 18.06.16
 */

public class LoggerImpl implements Logger {

    private final int mMinLogPriority;
    private final int mHistorySize;
    private final boolean mWriteToSysLog;
    private final List<LogListener> mLogListeners;
    private final EvictingQueue<LogMessage> mLogMessages;

    public LoggerImpl(int historySize, boolean writeToSysLog, LogType minLogPriority) {
        mMinLogPriority = minLogPriority.getPriority();
        mLogListeners = new ArrayList<>();
        mHistorySize = historySize;
        mWriteToSysLog = writeToSysLog;
        mLogMessages = EvictingQueue.create(mHistorySize);
    }

    private void log(LogType type, Object where, Throwable t, String messagePattern, Object... args) {
        LogMessage logMessage = new LogMessage(where, type, assembleMessage(t, messagePattern, args));
        if (mWriteToSysLog) {
            logToSysLog(logMessage);
        }
        if (logMessage.getLogType().getPriority() >= mMinLogPriority) {
            if (mHistorySize > 0) {
                synchronized (mLogMessages) {
                    mLogMessages.add(logMessage);
                }
            }
            synchronized (mLogListeners) {
                for (LogListener listener : mLogListeners) {
                    if (listener != null) {
                        listener.onLog(logMessage);
                    }
                }
            }
        }
    }

    public void addLogListener(LogListener logListener) {
        if (logListener == null) {
            throw new IllegalArgumentException("LogListener should not be null");
        }
        synchronized (mLogListeners) {
            if (!mLogListeners.contains(logListener)) {
                mLogListeners.add(logListener);
            }
        }
    }

    public synchronized void removeLogListener(LogListener logListener) {
        if (logListener == null) {
            throw new IllegalArgumentException("LogListener should not be null");
        }
        synchronized (mLogListeners) {
            mLogListeners.remove(logListener);
        }
    }

    private String assembleMessage(Throwable t, String message, Object... args) {
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message == null) {
            if (t == null) {
                return null;
            }
            message = getThrowableMessage(t);
        } else {
            if (args.length > 0) {
                message = String.format(message, args);
            }
            if (t != null) {
                message += "\n" + getThrowableMessage(t);
            }
        }
        return message;
    }

    private void logToSysLog(LogMessage logMessage) {
        switch (logMessage.getLogType()) {
            case DEBUG:
                Log.d(logMessage.getLoggedClassName(), logMessage.getMessage());
                break;
            case ERROR:
                Log.e(logMessage.getLoggedClassName(), logMessage.getMessage());
                break;
            case WARNING:
                Log.w(logMessage.getLoggedClassName(), logMessage.getMessage());
                break;
            case VERBOSE:
                Log.v(logMessage.getLoggedClassName(), logMessage.getMessage());
                break;
            case INFO:
                Log.i(logMessage.getLoggedClassName(), logMessage.getMessage());
                break;
        }
    }

    private String getThrowableMessage(Throwable t) {
        StringWriter stringWriter = new StringWriter(256);
        PrintWriter printWriter = new PrintWriter(stringWriter, false);
        t.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    @Override
    public void v(Object where, @NonNull String message, Object... args) {
        log(LogType.VERBOSE, where, null, message, args);
    }

    @Override
    public void v(Object where, Throwable t, @NonNull String message, Object... args) {
        log(LogType.VERBOSE, where, t, message, args);
    }

    @Override
    public void d(Object where, @NonNull String message, Object... args) {
        log(LogType.DEBUG, where, null, message, args);
    }

    @Override
    public void d(Object where, Throwable t, @NonNull String message, Object... args) {
        log(LogType.DEBUG, where, t, message, args);
    }

    @Override
    public void i(Object where, @NonNull String message, Object... args) {
        log(LogType.INFO, where, null, message, args);
    }

    @Override
    public void i(Object where, Throwable t, @NonNull String message, Object... args) {
        log(LogType.INFO, where, t, message, args);
    }

    @Override
    public void w(Object where, @NonNull String message, Object... args) {
        log(LogType.WARNING, where, null, message, args);
    }

    @Override
    public void w(Object where, Throwable t, @NonNull String message, Object... args) {
        log(LogType.WARNING, where, t, message, args);
    }

    @Override
    public void e(Object where, @NonNull String message, Object... args) {
        log(LogType.ERROR, where, null, message, args);
    }

    @Override
    public void e(Object where, Throwable t, @NonNull String message, Object... args) {
        log(LogType.ERROR, where, t, message, args);
    }

    @Override
    public Collection<LogMessage> getMessages(int limit) {
        List<LogMessage> resultList = new ArrayList<>(limit);
        synchronized (mLogMessages) {
            Iterator<LogMessage> messageIterator = mLogMessages.iterator();
            while (messageIterator.hasNext() && limit-- > 0) {
                resultList.add(messageIterator.next());
            }
        }
        return resultList;
    }

    @Override
    public Collection<LogMessage> getMessages() {
        return getMessages(mHistorySize);
    }

}
