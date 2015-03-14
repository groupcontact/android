package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import seaice.app.groupcontact.api.Callback;
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
        mConfigAPI.load(new Callback<ConfigAO>() {

            @Override
            public void call(ConfigAO config) {
                // save configuration into runtime constants
                Constants.baseUrl = config.getBaseUrl();
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                long uid = prefs.getLong("uid", -1);
                Intent intent;
                if (uid == -1) {
                    // goes to the edit activity
                    intent = new Intent(context, UserCreateActivity.class);
                    startActivity(intent);
                } else {
                    // goes to the main activity, and pass the uid into it
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                }
            }

            @Override
            public void error(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
