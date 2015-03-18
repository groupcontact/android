package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
public class StartupActivity extends DaggerActivity {

    @Inject
    ConfigAPI mConfigAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        getSupportActionBar().hide();

        final Context context = this;
        mConfigAPI.load(new BaseCallback<ConfigAO>(this) {
            @Override
            public void call(ConfigAO config) {
                // save configuration into runtime constants
                Constants.baseUrl = config.getBaseUrl();

                // check whether the user has logged in before.
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                long uid = prefs.getLong("uid", -1);
                Class<?> activityClass = UserCreateActivity.class;
                // Yes, the user logged in before.
                if (uid != -1) {
                    activityClass = MainActivity.class;
                    // save the uid and name as Runtime Constants.
                    Constants.uid = uid;
                    Constants.name = prefs.getString("name", "");
                }
                Intent intent = new Intent(context, activityClass);
                startActivity(intent);
            }
        });
    }

}
