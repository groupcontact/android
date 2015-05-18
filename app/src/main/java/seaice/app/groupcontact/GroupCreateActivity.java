package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.view.NavBarView;


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

    @InjectView(R.id.navBar)
    NavBarView mNavBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        ButterKnife.inject(this);

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });
    }

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
