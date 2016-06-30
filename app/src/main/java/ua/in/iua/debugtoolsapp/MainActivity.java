package ua.in.iua.debugtoolsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.in.iua.iuadebugtools.BuildConfig;
import ua.in.iua.iuadebugtools.DebugPanelDialog;
import ua.in.iua.iuadebugtools.LogAdapter;
import ua.in.iua.iuadebugtools.LogMessage;
import ua.in.iua.iuadebugtools.LogType;
import ua.in.iua.iuadebugtools.Logger;
import ua.in.iua.iuadebugtools.LoggerImpl;

public class MainActivity extends AppCompatActivity {

    private DebugPanelDialog mDebugPanelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowConsole = (Button) findViewById(R.id.btnShowConsole);

        assert btnShowConsole != null;

        final Logger logger = new LoggerImpl(100, true, LogType.DEBUG);

        logger.i(this, "Logger initialized");

        btnShowConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<LogMessage> messages = logger.getMessages();
                List<LogMessage> messageList = new ArrayList<>(messages);
                Collections.sort(messageList, new Comparator<LogMessage>() {
                    @Override
                    public int compare(LogMessage logMessage, LogMessage t1) {
                        return -logMessage.getDate().compareTo(t1.getDate());
                    }
                });

                if (mDebugPanelDialog == null) {
                    mDebugPanelDialog = DebugPanelDialog.newInstance(
                            "testServer",
                            BuildConfig.VERSION_NAME,
                            new LogAdapter(MainActivity.this, messageList));
                }
                mDebugPanelDialog.show(MainActivity.this);
            }
        });
    }
}
