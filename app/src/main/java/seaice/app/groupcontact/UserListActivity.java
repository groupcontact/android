package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import seaice.app.appbase.BaseActivity;
import seaice.app.appbase.view.ProgressView;
import seaice.app.appbase.view.TableView;
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
public class UserListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        TableView.OnCellClickListener {

    @Inject
    GroupAPI mGroupAPI;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    TableView mUserList;

    private UserListAdapter mAdapter;

    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout mLayout;

    private Long mGid;

    private ProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayout.setOnRefreshListener(this);
        mUserList.setVisibility(View.INVISIBLE);

        mGid = getIntent().getLongExtra("gid", -1L);
        String title = getIntent().getStringExtra("name");
        setTitle(title);
        mNavBarView.setTitle(title);

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

        mProgressView = ProgressView.show(this, getString(R.string.loading_user_list), true, null);

        mNavBarView.setRightActions(-1, getResources().getStringArray(
                R.array.group_actions), null, this);

        onRefresh();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_list;
    }

    @Override
    public void onRefresh() {
        mGroupAPI.list(mGid, new BaseCallback<List<UserAO>>(this) {
            @Override
            public void call(List<UserAO> result) {
                if (mLayout.isRefreshing()) {
                    mLayout.setRefreshing(false);
                } else {
                    mProgressView.dismiss();
                }
                if (result == null) {
                    info(getString(R.string.error_network));
                    return;
                }
                mUserList.setVisibility(View.VISIBLE);
                mAdapter.setDataSet(result);
            }
        });
    }

    @Override
    public void onCellClick(AdapterView<?> parent, View view, int section, int row, long id) {
        if (row == 0) {
            System.out.println("Setup More Information");
        } else if (row == 2) {
            System.out.println("Setup Privacy");
        } else if (row == 4) {
            mUserAPI.leaveGroup(Var.uid, Var.password, mGid, new BaseCallback<GeneralAO>(this) {
                @Override
                public void call(GeneralAO result) {
                    if (result.getStatus() == 1) {
                        info(getString(R.string.success_leave_group));
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        info(result.getInfo());
                    }
                }
            });
        }
    }
}
