package seaice.app.groupcontact;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import seaice.app.appbase.BaseActivity;
import seaice.app.appbase.view.ProgressView;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;

/**
 * This screen help the user to add friend directly, by enter the name and phone_normal number
 *
 * @author zhb
 */
public class UserAddActivity extends BaseActivity {

    @InjectView(R.id.user_add_name)
    EditText mNameView;

    @InjectView(R.id.user_add_phone)
    EditText mPhoneView;

    @Inject
    UserAPI mUserAPI;

    ProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");

        mNameView.setText(name);
        mPhoneView.setText(phone);

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressView = ProgressView.show(UserAddActivity.this,
                        getString(R.string.adding_friend), true, null);
                addFriend();
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_add;
    }

    public void addFriend() {
        String name = mNameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        mUserAPI.addFriend(Var.uid, Var.password, name, phone, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (mProgressView != null) {
                    mProgressView.dismiss();
                }
                if (result.getStatus() == 1) {
                    info(getString(R.string.success_add_friend));
                    Var.friendDataChanged = true;
                    finish();
                } else {
                    info(result.getInfo());
                }
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
}
