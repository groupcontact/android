package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.view.NavBarView;

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

    @InjectView(R.id.navBar)
    NavBarView mNavBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        ButterKnife.inject(this);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");

        mNameView.setText(name);
        mPhoneView.setText(phone);

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
    }

    public void addFriend() {
        String name = mNameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        mUserAPI.addFriend(Var.uid, Var.password, name, phone, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (result.getStatus() == 1) {
                    info(getString(R.string.success_add_friend));
                    // The question here is that do we suppose the user will add friend in batch mode
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
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
