package seaice.app.groupcontact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xiaomi.market.sdk.XiaomiUpdateAgent;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

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

        setContentView(R.layout.activity_startup);

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
                // Yes, the user logged in before.
                if (uid != -1) {
                    String password = prefs.getString("password", "123456");
                    // 升级前没有存储name字段
                    String name = prefs.getString("name", null);
                    if (name == null && config == null) {
                        info(getString(R.string.network_required_for_upgrade));
                        return;
                    }
                    loadUserInfo(uid, name, password);
                } else {
                    Intent intent = new Intent(context, UserCreateActivity.class);
                    startActivityForResult(intent, Let.REQUEST_CODE_CREATE_USER);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Let.REQUEST_CODE_CREATE_USER) {
            if (resultCode == Activity.RESULT_OK) {
                long uid = data.getLongExtra("uid", -1);
                String name = data.getStringExtra("name");
                String password = data.getStringExtra("password");
                loadUserInfo(uid, name, password);
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        finish();
    }

    private void loadUserInfo(long uid, String name, String password) {
        Var.uid = uid;
        Var.name = name;
        Var.password = password;

        // load user info
        mUserAPI.find(Var.uid, new BaseCallback<List<UserAO>>(this) {
            @Override
            public void call(List<UserAO> result) {
                // 如果网络有错误，则从本地读取
                if (result == null || result.size() == 0) {
                    result = FileUtils.read(StartupActivity.this, Let.PROFILE_CACHE_PATH, UserAO.class);
                } else {
                    FileUtils.write(StartupActivity.this, Let.PROFILE_CACHE_PATH, result, UserAO.class, true);
                    getSharedPreferences("prefs", MODE_PRIVATE).edit().putString("name", result.get(0)
                            .getName()).apply();
                }
                Var.userAO = result.get(0);
                Intent intent = new Intent(StartupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
