package seaice.app.groupcontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RadioGroup;

import java.util.Arrays;

import info.hoang8f.android.segmented.SegmentedGroup;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.fragment.LoginFragment;
import seaice.app.groupcontact.fragment.RegisterFragment;
import seaice.app.groupcontact.utils.FileUtils;

/**
 * create a user instance in the app..
 *
 * @author zhb
 */
public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SegmentedGroup segmentedGroup = (SegmentedGroup) LayoutInflater.from(this).inflate(
                R.layout.segment_control_login_register, null);
        mNavBarView.setCenterItem(segmentedGroup);

        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_auth, new RegisterFragment()).commit();

        segmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.login) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fragment_auth, new LoginFragment()).commit();
                } else if (checkedId == R.id.register) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fragment_auth, new RegisterFragment()).commit();
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_auth;
    }

    public void setUser(UserAO user, String password) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        prefs.edit().putLong("uid", user.getUid()).apply();
        prefs.edit().putString("name", user.getName()).apply();
        prefs.edit().putString("password", password).apply();
        Var.userAO = user;
        Var.uid = user.getUid();
        Var.name = user.getName();
        Var.password = password;
        // 保存到文件中
        FileUtils.write(this, Let.PROFILE_CACHE_PATH, Arrays.asList(user), UserAO.class, true);
        // 准备就绪
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
