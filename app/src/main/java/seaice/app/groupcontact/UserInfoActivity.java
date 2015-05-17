package seaice.app.groupcontact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.adapter.UserInfoAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.view.NavBarView;
import seaice.app.groupcontact.view.TableView;


public class UserInfoActivity extends BaseActivity {

    @InjectView(R.id.navBar)
    NavBarView mNavBarView;

    @InjectView(R.id.filedList)
    TableView mTableView;

    @Inject
    UserAPI mUserAPI;

    UserAO mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);

        ButterKnife.inject(this);

        mUser = getIntent().getParcelableExtra("user");
        setTitle(mUser.getName());
        mNavBarView.setTitle(mUser.getName());

        mTableView.setAdapter(new UserInfoAdapter(this, mUser));
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
//
//    @OnClick(R.id.call)
//    public void call() {
//        // call
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + mPhoneView.getText().toString()));
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.sendMessage)
//    public void sendMessage() {
//        // sms
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("smsto:" + mPhoneView.getText().toString()));
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.sendEmail)
//    public void sendEmail() {
//        // email
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mEmailView.getText().toString()});
//        intent.setType("message/rfc822");
//        startActivity(intent);
//    }
//
//    public void addFriend() {
//        mUserAPI.addFriend(Var.uid, Var.password, mUser.getName(), mUser.getPhone(),
//                new BaseCallback<GeneralAO>(this) {
//                    public void call(GeneralAO result) {
//                        if (result == null) {
//                            info(getString(R.string.error_network));
//                            return;
//                        }
//                        if (result.getStatus() == 1) {
//                            info(getString(R.string.success_add_friend));
//                            finish();
//                        } else {
//                            info(result.getInfo());
//                        }
//                    }
//                });
//    }
//
//    public void deleteFriend() {
//        mUserAPI.deleteFriend(Var.uid, Var.password, mUser.getUid(),
//                new BaseCallback<GeneralAO>(this) {
//                    public void call(GeneralAO result) {
//                        if (result == null) {
//                            info(getString(R.string.error_network));
//                            return;
//                        }
//                        if (result.getStatus() == 1) {
//                            info(getString(R.string.success_delete_friend));
//                            Intent returnIntent = new Intent();
//                            setResult(RESULT_OK, returnIntent);
//                            finish();
//                        } else {
//                            info(result.getInfo());
//                        }
//                    }
//                });
//    }
}
