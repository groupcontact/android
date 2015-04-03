package seaice.app.groupcontact;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;

/**
 * This screen help the user to add friend directly, by enter the name and phone number
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.user_add_add)
    public void addFriend() {
        String name = mNameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        mUserAPI.addFriend(Constants.uid, name, phone, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (result.getStatus() == -1) {
                    info(result.getInfo());
                    return;
                }
                info(getString(R.string.success_add_friend));
                // The question here is that do we suppose the user will add friend in batch mode
                // finish();
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
