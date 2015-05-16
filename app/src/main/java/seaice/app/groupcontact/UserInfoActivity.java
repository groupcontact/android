package seaice.app.groupcontact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;


public class UserInfoActivity extends BaseActivity {

    @InjectView(R.id.phone)
    TextView mPhoneView;

    @InjectView(R.id.email)
    TextView mEmailView;

    @InjectView(R.id.wechat)
    TextView mWechatView;

    @InjectView(R.id.action_add_friend)
    Button mAddButton;

    @InjectView(R.id.action_remove_friend)
    Button mDeleteButton;

    @Inject
    UserAPI mUserAPI;

    UserAO mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);

        ButterKnife.inject(this);

        int from = getIntent().getIntExtra("from", 0);
        if (from == 1) {
            mAddButton.setVisibility(View.GONE);
        } else {
            mDeleteButton.setVisibility(View.GONE);
        }

        mUser = getIntent().getParcelableExtra("user");
        if (mUser.getUid() == Var.uid) {
            mAddButton.setVisibility(View.GONE);
            mDeleteButton.setVisibility(View.GONE);
        }
        mPhoneView.setText(mUser.getPhone());
        try {
            JSONObject extObj = new JSONObject(mUser.getExt());
            mEmailView.setText(extObj.optString("email", ""));
            mWechatView.setText(extObj.optString("wechat", ""));
        } catch (JSONException e) {
            // ignore this
        }
        setTitle(mUser.getName());
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

    @OnClick(R.id.call)
    public void call() {
        // call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mPhoneView.getText().toString()));
        startActivity(intent);
    }

    @OnClick(R.id.sendMessage)
    public void sendMessage() {
        // sms
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + mPhoneView.getText().toString()));
        startActivity(intent);
    }

    @OnClick(R.id.sendEmail)
    public void sendEmail() {
        // email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mEmailView.getText().toString()});
        intent.setType("message/rfc822");
        startActivity(intent);
    }

    @OnClick(R.id.action_add_friend)
    public void addFriend() {
        mUserAPI.addFriend(Var.uid, Var.password, mUser.getName(), mUser.getPhone(),
                new BaseCallback<GeneralAO>(this) {
                    public void call(GeneralAO result) {
                        if (result == null) {
                            info(getString(R.string.error_network));
                            return;
                        }
                        if (result.getStatus() == 1) {
                            info(getString(R.string.success_add_friend));
                            finish();
                        } else {
                            info(result.getInfo());
                        }
                    }
                });
    }

    @OnClick(R.id.action_remove_friend)
    public void deleteFriend() {
        mUserAPI.deleteFriend(Var.uid, Var.password, mUser.getUid(),
                new BaseCallback<GeneralAO>(this) {
                    public void call(GeneralAO result) {
                        if (result == null) {
                            info(getString(R.string.error_network));
                            return;
                        }
                        if (result.getStatus() == 1) {
                            info(getString(R.string.success_delete_friend));
                            Intent returnIntent = new Intent();
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        } else {
                            info(result.getInfo());
                        }
                    }
                });
    }
}
