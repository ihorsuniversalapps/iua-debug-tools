package ua.in.iua.debugtoolsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ua.in.iua.iuadebugtools.BuildConfig;
import ua.in.iua.iuadebugtools.DebugPanelDialog;

public class MainActivity extends AppCompatActivity {

    private DebugPanelDialog mDebugPanelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowConsole = (Button) findViewById(R.id.btnShowConsole);

        assert btnShowConsole != null;

        btnShowConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDebugPanelDialog == null) {
                    mDebugPanelDialog = DebugPanelDialog.newInstance("testServer", BuildConfig.VERSION_NAME);
                }
                mDebugPanelDialog.show(MainActivity.this);
            }
        });
    }
}
