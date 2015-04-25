package seaice.app.groupcontact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * The Screen Shows the listGroup of users in the specified group.
 *
 * @author zhb
 */
public class UserListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    GroupAPI mGroupAPI;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    ListView mUserList;

    private UserListAdapter mAdapter;

    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout mLayout;

    private Long mGid;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ButterKnife.inject(this);

        mLayout.setOnRefreshListener(this);
        mUserList.setVisibility(View.INVISIBLE);
        // enable home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGid = getIntent().getLongExtra("gid", -1L);
        setTitle(getIntent().getStringExtra("name"));

        mAdapter = new UserListAdapter(this, true);
        mUserList.setAdapter(mAdapter);

        final Context context = this;
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserAO user = (UserAO) parent.getAdapter().getItem(position);
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("user", user);
                // 从群组列表进来
                intent.putExtra("from", 0);
                startActivity(intent);
            }
        });

        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getResources().getString(R.string.loading_user_list));
        mDialog.setCancelable(true);
        mDialog.show();

        onRefresh();
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_user_list, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mGroupAPI.list(mGid, new BaseCallback<List<UserAO>>(this) {
            @Override
            public void call(List<UserAO> result) {
                if (mLayout.isRefreshing()) {
                    mLayout.setRefreshing(false);
                } else {
                    mDialog.dismiss();
                }
                if (result == null) {
                    info(getString(R.string.error_network));
                    return;
                }
                mUserList.setVisibility(View.VISIBLE);
                mAdapter.setDataset(result);
            }
        });
    }

    public void leaveGroup() {
        mUserAPI.leaveGroup(Var.uid, Var.password, mGid, new BaseCallback<GeneralAO>(this) {
            @Override
            public void call(GeneralAO result) {
                if (result.getStatus() == 1) {
                    info(getString(R.string.success_leave_group));
                    finish();
                } else {
                    info(result.getInfo());
                }
            }
        });
    }
}
