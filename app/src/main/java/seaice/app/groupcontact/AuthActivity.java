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

//    @InjectView(R.id.user_create_code)
//    EditText mCodeView;
//
//    @InjectView(R.id.user_create_phone)
//    EditText mPhoneView;
//
//    @InjectView(R.id.user_create_name)
//    EditText mNameView;
//
//    @Inject
//    UserAPI mUserAPI;
//
//    @InjectView(R.id.user_create_create)
//    Button mCreateView;
//
//    private ProgressDialog mDialog;
//
//    private Long mUserId;

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

//        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        mPhoneView.setText(tMgr.getLine1Number());
//
//        mDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_auth;
    }

    //    @OnClick(R.id.user_create_create)
//    public void createUser() {
//        String phone = mPhoneView.getText().toString();
//        String password = mCodeView.getText().toString();
//        String name = mNameView.getText().toString();
//
//        mDialog.setMessage(getString(R.string.progress_wait));
//        mDialog.setCancelable(true);
//        mDialog.show();
//
//        if (mCreateView.getText().toString().equals(getString(R.string.user_create_save))) {
//            save(name, phone, password);
//        } else {
//            register(phone, password);
//        }
//    }
//
//    private void register(String phone, String password) {
//        mUserAPI.register(phone, password, new BaseCallback<GeneralAO>(this) {
//            @Override
//            public void call(GeneralAO result) {
//                mDialog.dismiss();
//                if (result == null) {
//                    info(getString(R.string.error_network));
//                    return;
//                }
//                mUserId = result.getId();
//                if (result.getStatus() == 1) {
//                    // 再输入用户名
//                    AnimationUtils.expand(mNameView);
//                    mNameView.requestFocus();
//                    // 手机号输入和密码输入需要被禁掉
//                    mPhoneView.setEnabled(false);
//                    mCodeView.setEnabled(false);
//                    // 按钮文字也需要改成保存并继续
//                    mCreateView.setText(getString(R.string.user_create_save));
//                } else if (result.getStatus() == 2) {
//                    success();
//                } else {
//                    info(result.getInfo());
//                }
//            }
//        });
//    }
//
//    private void save(String name, String phone, String password) {
//        UserAO user = new UserAO();
//        user.setUid(mUserId);
//        user.setName(name);
//        user.setPhone(phone);
//        user.setExt("{}");
//        mUserAPI.save(user, mCodeView.getText().toString(), new BaseCallback<GeneralAO>(this) {
//            @Override
//            public void call(GeneralAO result) {
//                if (result == null) {
//                    info(getString(R.string.error_network));
//                    return;
//                }
//                if (result.getStatus() == 1) {
//                    success();
//                } else {
//                    info(result.getInfo());
//                }
//            }
//        });
//    }
//
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
