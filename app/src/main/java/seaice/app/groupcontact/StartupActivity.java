package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.xiaomi.market.sdk.XiaomiUpdateAgent;

import javax.inject.Inject;

import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.ConfigAPI;
import seaice.app.groupcontact.api.ao.ConfigAO;

/**
 * The startup activity display a placeholder screen while doing the background tasks(currently
 * there is just one task: loading configuration from server) to get initialized.
 *
 * @author zhb
 */
public class StartupActivity extends BaseActivity {

    @Inject
    ConfigAPI mConfigAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XiaomiUpdateAgent.update(this);

        setContentView(R.layout.activity_startup);

        getSupportActionBar().hide();

        final Context context = this;
        mConfigAPI.load(new BaseCallback<ConfigAO>(this) {
            @Override
            public void call(ConfigAO config) {
                boolean networkStatus = true;

                // there is an error accessing internet
                if (config == null) {
                    config = new ConfigAO();
                    networkStatus = false;
                }

                // save configuration into runtime constants
                Var.baseUrl = config.getBaseUrl();

                // check whether the user has logged in before.
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                long uid = prefs.getLong("uid", -1);
                Class<?> activityClass = UserCreateActivity.class;
                // Yes, the user logged in before.
                if (uid != -1) {
                    activityClass = MainActivity.class;
                    Var.uid = uid;
                    Var.password = prefs.getString("password", "123456");
                }
                Intent intent = new Intent(context, activityClass);
                intent.putExtra("NetworkStatus", networkStatus);
                startActivity(intent);
            }
        });
    }

}
