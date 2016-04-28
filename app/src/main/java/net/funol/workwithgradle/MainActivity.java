package net.funol.workwithgradle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.main_text);

        String info = "BuildConfigs:\nHOST:" + BuildConfig.HOST
                + "\nisRelease:" + BuildConfig.isRelease
                + "\nversionCode:" + BuildConfig.versionCode;

        mText.setText(info);
    }
}
