package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends FragmentActivity {

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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent.getStringExtra("title") == null) {
            intent.putExtra("title", getTitle());
        }
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(Intent intent) {
        if (intent.getStringExtra("title") == null) {
            intent.putExtra("title", getTitle());
        }
        super.startActivity(intent);
    }
}
