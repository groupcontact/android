package seaice.app.groupcontact;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import seaice.app.appbase.BaseActivity;
import seaice.app.appbase.view.ProgressView;
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

    ProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressView = ProgressView.show(GroupCreateActivity.this,
                        getString(R.string.creating_group), true, null);
                createGroup();
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_group_create;
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
            @Override
            public void call(GeneralAO result) {
                if (mProgressView != null) {
                    mProgressView.dismiss();
                }
                if (result.getStatus() == 1) {
                    info(getString(R.string.success_create_user));
                    Var.groupDataChanged = true;
                    finish();
                } else {
                    info(result.getInfo());
                }
            }
        });
    }
}
