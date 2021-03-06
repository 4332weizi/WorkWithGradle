package net.funol.workwithgradle;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
                + "\nversionCode:" + BuildConfig.versionCode
                + "\nDEMO_VALUE:" + getMetaData("DEMO_NAME")
                + "\nUMENG_APP_KEY:" + getMetaData("UMENG_APP_KEY")
                + "\nUMENG_CHANNEL:" + getMetaData("UMENG_CHANNEL")
                + "\nAMAP_API_KEY:" + getMetaData("AMAP_API_KEY");

        mText.setText(info);
    }

    private String getMetaData(String name) {
        try {
            ApplicationInfo info = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
