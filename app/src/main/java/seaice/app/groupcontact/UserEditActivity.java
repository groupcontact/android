package seaice.app.groupcontact;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.FileUtils;
import seaice.app.groupcontact.view.NavBarView;


public class UserEditActivity extends BaseActivity {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.navBar)
    NavBarView mNavBarView;

    @InjectView(R.id.editPhone)
    EditText mPhoneView;

    @InjectView(R.id.editEmail)
    EditText mEmailView;

    @InjectView(R.id.editWechat)
    EditText mWechatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        ButterKnife.inject(this);

        UserAO user = Var.userAO;
        mPhoneView.setText(user.getPhone());
        try {
            JSONObject extObj = new JSONObject(user.getExt());
            mEmailView.setText(extObj.optString("email", ""));
            mWechatView.setText(extObj.optString("wechat", ""));
        } catch (JSONException e) {
            // ignore this;
        }

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        final UserAO user = new UserAO();
        user.setUid(Var.uid);
        user.setName(Var.userAO.getName());
        user.setPhone(mPhoneView.getText().toString());
        JSONObject extObj = new JSONObject();
        try {
            extObj.put("email", mEmailView.getText().toString());
            extObj.put("wechat", mWechatView.getText().toString());
            user.setExt(extObj.toString());
        } catch (JSONException e) {
            user.setExt("{}");
        }
        mUserAPI.save(user, Var.password, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (result == null) {
                    info(getString(R.string.error_network));
                    return;
                }
                if (result.getStatus() == 1) {
                    Toast.makeText(mContext, mContext.getResources().getText(
                            R.string.success_save_user), Toast.LENGTH_LONG).show();
                    Var.userAO = user;
                    FileUtils.write(UserEditActivity.this, Let.PROFILE_CACHE_PATH, Arrays.asList(user),
                            UserAO.class, true);
                } else {
                    Toast.makeText(mContext, mContext.getResources().getText(
                            R.string.fail_save_user), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
