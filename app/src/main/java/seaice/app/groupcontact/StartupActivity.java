package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.xiaomi.market.sdk.XiaomiUpdateAgent;

import java.io.File;

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

        File file = new File(Let.APP_DIR);
        file.mkdirs();

        final Context context = this;
        mConfigAPI.load(new BaseCallback<ConfigAO>(this) {
            @Override
            public void call(ConfigAO config) {
                if (config == null) {
                    info(getString(R.string.error_network));
                } else {
                    // save configuration into runtime constants
                    Var.config = config;
                }
                // check whether the user has logged in before.
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                long uid = prefs.getLong("uid", -1);
                Class<?> activityClass = UserCreateActivity.class;
                // Yes, the user logged in before.
                if (uid != -1) {
                    activityClass = MainActivity.class;
                    Var.uid = uid;
                    Var.password = prefs.getString("password", "123456");
                    String name = prefs.getString("name", null);
                    if (name == null && config == null) {
                        info(getString(R.string.network_required_for_upgrade));
                        return;
                    }
                }
                Intent intent = new Intent(context, activityClass);
                startActivity(intent);
            }
        });

    }
}
