package seaice.app.groupcontact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.AnimationUtils;

/**
 * create a user instance in the app..
 *
 * @author zhb
 */
public class UserCreateActivity extends BaseActivity {

    @InjectView(R.id.user_create_password)
    EditText mPasswordView;

    @InjectView(R.id.user_create_phone)
    EditText mPhoneView;

    @InjectView(R.id.user_create_name)
    EditText mNameView;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.user_create_create)
    Button mCreateView;

    private ProgressDialog mDialog;

    private Long mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        ButterKnife.inject(this);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneView.setText(tMgr.getLine1Number());

        mDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @OnClick(R.id.user_create_create)
    public void createUser() {
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();

        mDialog.setMessage(getString(R.string.progress_wait));
        mDialog.setCancelable(true);
        mDialog.show();

        if (mCreateView.getText().toString().equals(getString(R.string.user_create_save))) {
            save(name, phone, password);
        } else {
            register(phone, password);
        }
    }

    private void register(String phone, String password) {
        mUserAPI.register(phone, password, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                mDialog.dismiss();
                mUserId = result.getId();
                if (result.getStatus() == 1) {
                    // 再输入用户名
                    AnimationUtils.expand(mNameView);
                    mNameView.requestFocus();
                    // 手机号输入和密码输入需要被禁掉
                    mPhoneView.setEnabled(false);
                    mPasswordView.setEnabled(false);
                    // 按钮文字也需要改成保存并继续
                    mCreateView.setText(getString(R.string.user_create_save));
                } else if (result.getStatus() == 2) {
                    success();
                } else {
                    info(result.getInfo());
                }
            }
        });
    }

    private void save(String name, String phone, String password) {
        UserAO user = new UserAO();
        user.setUid(mUserId);
        user.setName(name);
        user.setPhone(phone);
        user.setExt("{}");
        mUserAPI.save(user, mPasswordView.getText().toString(), new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (result.getStatus() == 1) {
                    success();
                } else {
                    info(result.getInfo());
                }
            }
        });
    }

    private void success() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        prefs.edit().putLong("uid", mUserId).apply();
        prefs.edit().putString("password", mPasswordView.getText().toString()).apply();
        Constants.uid = mUserId;
        Constants.password = mPasswordView.getText().toString();

        Intent intent = new Intent(UserCreateActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
