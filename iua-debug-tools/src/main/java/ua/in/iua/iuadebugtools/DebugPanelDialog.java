package ua.in.iua.iuadebugtools;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DebugPanelDialog extends BaseFullscreenDialog {

    private final static String TAG = "DebugPanelDialog";
    final private Handler mHandler = new Handler();
    private String mServerName;
    private String mApplicationVersion;
    private Runnable mRunnable;

    public static DebugPanelDialog newInstance(String serverName, String applicationVersion) {
        DebugPanelDialog fragment = new DebugPanelDialog();
        fragment.mServerName = serverName;
        fragment.mApplicationVersion = applicationVersion;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug, container, false);
        TextView tvServerName = (TextView) view.findViewById(R.id.tvServerName);
        TextView tvApplicationVersion = (TextView) view.findViewById(R.id.tvApplicationVersion);

        tvServerName.setText(String.format("Server: %s", mServerName));
        tvApplicationVersion.setText(String.format("Version: %s", mApplicationVersion));

        ListView lvLog = (ListView) view.findViewById(R.id.lvLog);
        lvLog.setAdapter(new LogAdapter(getActivity(), Logger.getInstance().getLogMessages()));

        setCancelable(false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setDimAmount(0.7f);

        Button btnClose = (Button) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void show(int delay, final AppCompatActivity activity) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                show(activity);
            }
        };
        mHandler.postDelayed(mRunnable, delay);
    }

    public void show(AppCompatActivity activity) {
        if (activity != null && !activity.isFinishing() && !isShowing() && !isAdded()) {
            super.show(activity.getSupportFragmentManager(), TAG);
        }
    }

    public void show(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null && !fragmentManager.isDestroyed() && !isShowing() || !isAdded()) {
                super.show(fragmentManager, TAG);
            }
        }
    }

    public boolean isShowing() {
        Dialog dialog = getDialog();
        return dialog != null && dialog.isShowing();
    }

    @Override
    public void dismiss() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mRunnable = null;
        }
        if (isShowing()) {
            try {
                super.dismissAllowingStateLoss();
            } catch (IllegalStateException ignored) {

            }
        }
    }
}
