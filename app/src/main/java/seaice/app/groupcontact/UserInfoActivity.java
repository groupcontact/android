package seaice.app.groupcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.api.ao.UserAO;


public class UserInfoActivity extends ActionBarActivity {

    @InjectView(R.id.editName)
    EditText mNameView;

    @InjectView(R.id.editPhone)
    EditText mPhoneView;

    @InjectView(R.id.editEmail)
    EditText mEmailView;

    @InjectView(R.id.editWechat)
    EditText mWechatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPhoneView.setEnabled(false);
        mEmailView.setEnabled(false);
        mWechatView.setEnabled(false);

        UserAO user = getIntent().getParcelableExtra("user");
        mNameView.setText(user.getName());
        mPhoneView.setText(user.getPhone());
        try {
            JSONObject extObj = new JSONObject(user.getExt());
            mEmailView.setText(extObj.optString("email", ""));
            mWechatView.setText(extObj.optString("wechat", ""));
        } catch (JSONException e) {
            // ignore this
        }
        setTitle(user.getName());
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
