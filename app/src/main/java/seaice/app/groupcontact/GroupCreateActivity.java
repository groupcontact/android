package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;


public class GroupCreateActivity extends BaseActivity {

    @Inject
    GroupAPI mGroupAPI;

    @InjectView(R.id.group_create_name)
    EditText mName;

    @InjectView(R.id.group_create_desc)
    EditText mDesc;

    @InjectView(R.id.group_create_access)
    EditText mAccess;

    @InjectView(R.id.group_create_modify)
    EditText mModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        ButterKnife.inject(this);

        // enable home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.group_create_create)
    public void createGroup() {
        String name = mName.getText().toString();
        String desc = mDesc.getText().toString();
        String access = mAccess.getText().toString();
        String modify = mModify.getText().toString();

        GroupAO group = new GroupAO();
        group.setName(name);
        group.setDesc(desc);
        group.setAccessToken(access);
        group.setModifyToken(modify);

        mGroupAPI.create(Var.uid, Var.password, group, new BaseCallback<GeneralAO>(this) {
            public void call(GeneralAO result) {
                if (result.getStatus() == 1) {
                    info(getString(R.string.success_create_user));
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
