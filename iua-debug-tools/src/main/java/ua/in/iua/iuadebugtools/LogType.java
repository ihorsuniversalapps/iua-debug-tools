package ua.in.iua.iuadebugtools;

/**
 * Log Type.
 * Created by rusin on 28.06.16.
 */
public enum LogType {
    INFO(4),
    WARNING(5),
    DEBUG(3),
    ERROR(6),
    VERBOSE(2);

    private int mPriority;

    LogType(int priority) {
        mPriority = priority;
    }

    public int getPriority() {
        return mPriority;
    }
}