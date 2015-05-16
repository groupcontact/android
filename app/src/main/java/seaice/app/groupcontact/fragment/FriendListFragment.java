package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.Let;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserInfoActivity;
import seaice.app.groupcontact.Var;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.FileUtils;
import seaice.app.groupcontact.view.TableView;

public class FriendListFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int MANAGE_FRIEND = 2;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    TableView mUserList;

    private UserListAdapter mAdapter;

    private SwipeRefreshLayout mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.inject(this, mLayout);
        mLayout.setOnRefreshListener(this);

        mAdapter = new UserListAdapter(getActivity(), false);
        List<UserAO> dataset = FileUtils.read(getActivity(), Let.FRIEND_CACHE_PATH, UserAO.class);
        // No Cache
        if (dataset == null || dataset.size() == 0) {
            onRefresh();
        } else {
            mAdapter.setDataSet(dataset);
        }

        mUserList.setAdapter(mAdapter);
        mUserList.setOnItemClickListener(this);

        return mLayout;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friend_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_friend) {
            Intent intent = new Intent(getActivity(), UserAddActivity.class);
            startActivityForResult(intent, MANAGE_FRIEND);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserAO user = (UserAO) parent.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        intent.putExtra("user", user);
        // 从好友列表进来
        intent.putExtra("from", 1);
        startActivityForResult(intent, MANAGE_FRIEND);
    }

    @Override
    public void onRefresh() {
        Context context = getActivity();

        mUserAPI.listFriend(Var.uid, new BaseCallback<List<UserAO>>(context) {
            @Override
            public void call(List<UserAO> result) {
                if (result == null) {
                    // 如果是第一次请求数据，则直接从本地读取
                    if (mAdapter.getCount() == 0) {
                        mAdapter.setDataSet(FileUtils.read(getActivity(), Let.FRIEND_CACHE_PATH,
                                UserAO.class));
                    }
                    // 用户想刷新，弹出提示
                    else {
                        info(getString(R.string.error_network));
                    }
                } else {
                    FileUtils.write(getActivity(), Let.FRIEND_CACHE_PATH, result, UserAO.class,
                            result.size() != mAdapter.getCount());
                    mAdapter.setDataSet(result);
                }
                mLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MANAGE_FRIEND) {
            if (resultCode == Activity.RESULT_OK) {
                onRefresh();
            }
        }
    }

}
