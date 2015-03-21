package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * create a user instance in the app..
 *
 * @author zhb
 */
public class UserCreateActivity extends DaggerActivity {

    @InjectView(R.id.user_create_name)
    EditText mNameView;

    @InjectView(R.id.user_create_phone)
    EditText mPhoneView;

    @Inject
    UserAPI mUserAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.user_create_create)
    public void createUser() {
        final Context context = this;

        UserAO user = new UserAO();
        user.setName(mNameView.getText().toString());
        user.setPhone(mPhoneView.getText().toString());
        mUserAPI.create(user, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                // failed to load resource
                if (result.getStatus() == -1) {
                    error(result.getInfo());
                    return;
                }
                // yes, the user logged in
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                Long uid = result.getId();
                String name = mNameView.getText().toString();
                prefs.edit().putLong("uid", uid).commit();
                Constants.uid = uid;
                prefs.edit().putString("name", name).commit();
                Constants.name = name;
                // goes to the main activity
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
