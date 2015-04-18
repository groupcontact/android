package seaice.app.groupcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.api.ao.UserAO;


public class UserInfoActivity extends ActionBarActivity {

    @InjectView(R.id.phone)
    TextView mPhoneView;

    @InjectView(R.id.email)
    TextView mEmailView;

    @InjectView(R.id.wechat)
    TextView mWechatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);

        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserAO user = getIntent().getParcelableExtra("user");
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
