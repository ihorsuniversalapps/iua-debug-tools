package ua.in.iua.iuadebugtools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/***
 * Debug console log messages adapter.
 */
public class LogAdapter extends BaseAdapter {

    private final List<LogMessage> mLogMessages;
    private final Context mContext;

    public LogAdapter(Context context, List<LogMessage> logMessages) {
        mLogMessages = logMessages;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mLogMessages.size();
    }

    @Override
    public LogMessage getItem(int position) {
        return mLogMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).toString().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.log_message_item, parent, false);
            ViewHolder holder = new ViewHolder(
                    view.findViewById(R.id.llLog),
                    (TextView) view.findViewById(R.id.tvLogType),
                    (TextView) view.findViewById(R.id.tvLogDateTime),
                    (TextView) view.findViewById(R.id.tvLogClassName),
                    (TextView) view.findViewById(R.id.tvLogMessage));
            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        LogMessage item = getItem(position);

        holder.getLogClassName().setText(item.getLoggedClassName());
        holder.getLogDateTime().setText(item.getDate().toString());
        holder.getLogMessage().setText(item.getMessage());

        switch (item.getLogType()) {
            case INFO:
                holder.getLogType().setText(R.string.info);
                holder.getBackgroundView().setBackgroundColor(0xffaaffaa);
                break;
            case WARNING:
                holder.getLogType().setText(R.string.warning);
                holder.getBackgroundView().setBackgroundColor(0xffffffaa);
                break;
            case DEBUG:
                holder.getLogType().setText(R.string.debug);
                holder.getBackgroundView().setBackgroundColor(0xffffaaff);
                break;
            case ERROR:
                holder.getLogType().setText(R.string.error);
                holder.getBackgroundView().setBackgroundColor(0xffffaaaa);
                break;
            case VERBOSE:
                holder.getLogType().setText(R.string.verbose);
                holder.getBackgroundView().setBackgroundColor(0xffffffff);
                break;
        }

        return view;
    }

    private class ViewHolder {
        private final View mBackgroundView;
        private final TextView mLogType;
        private final TextView mLogDateTime;
        private final TextView mLogClassName;
        private final TextView mLogMessage;

        public ViewHolder(View backgroundView, TextView logType, TextView logDateTime, TextView logClassName, TextView logMessage) {
            mBackgroundView = backgroundView;
            mLogType = logType;
            mLogDateTime = logDateTime;
            mLogClassName = logClassName;
            mLogMessage = logMessage;
        }

        public View getBackgroundView() {
            return mBackgroundView;
        }

        public TextView getLogType() {
            return mLogType;
        }

        public TextView getLogDateTime() {
            return mLogDateTime;
        }

        public TextView getLogClassName() {
            return mLogClassName;
        }

        public TextView getLogMessage() {
            return mLogMessage;
        }
    }
}
