package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.xiaomi.market.sdk.XiaomiUpdateAgent;

import java.io.File;

import javax.inject.Inject;

import seaice.app.appbase.BaseActivity;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.ConfigAPI;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.ConfigAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.FileUtils;

/**
 * The startup activity display a placeholder screen while doing the background tasks(currently
 * there is just one task: loading configuration from server) to get initialized.
 *
 * @author zhb
 */
public class StartupActivity extends BaseActivity {

    @Inject
    ConfigAPI mConfigAPI;

    @Inject
    UserAPI mUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XiaomiUpdateAgent.update(this);

        File file = new File(Let.APP_DIR);
        if (!file.mkdirs()) {
            Log.e("StartupActivity", "Failed To Make Directories.");
        }

        final Context context = this;
        mConfigAPI.load(new BaseCallback<ConfigAO>(this) {
            @Override
            public void call(ConfigAO config) {
                if (config == null) {
                    info(getString(R.string.error_network));
                } else {
                    // 保存配置
                    Var.config = config;
                }
                // 用户是否已经登录了
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                long uid = prefs.getLong("uid", -1);
                // 登录过
                if (uid != -1) {
                    Var.uid = uid;
                    Var.name = prefs.getString("name", "");
                    Var.password = prefs.getString("password", "");
                    Var.userAO = FileUtils.read(context, Let.PROFILE_CACHE_PATH, UserAO.class).get(0);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, AuthActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_startup;
    }

    @Override
    protected boolean hasNavBar() {
        return false;
    }
}
