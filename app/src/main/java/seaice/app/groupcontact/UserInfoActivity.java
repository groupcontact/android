package seaice.app.groupcontact;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import seaice.app.groupcontact.api.ao.UserAO;


public class UserInfoActivity extends ActionBarActivity {

    private EditText mNameView;

    private EditText mPhoneView;

    private EditText mEmailView;

    private EditText mWechatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameView = (EditText) findViewById(R.id.editName);
        mPhoneView = (EditText) findViewById(R.id.editPhone);
        mPhoneView.setEnabled(false);
        mEmailView = (EditText) findViewById(R.id.editEmail);
        mEmailView.setEnabled(false);
        mWechatView = (EditText) findViewById(R.id.editWechat);
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
