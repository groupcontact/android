package seaice.app.groupcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dagger need this for injection
        MyApplication.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
