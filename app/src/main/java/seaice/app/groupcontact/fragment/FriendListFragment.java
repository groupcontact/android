package seaice.app.groupcontact.fragment;

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
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.RuntimeVar;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserInfoActivity;
import seaice.app.groupcontact.adapter.AddFriendMenuAdapter;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.UserAO;

public class FriendListFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    ListView mUserList;

    private UserListAdapter mAdapter;

    private SwipeRefreshLayout mLayout;

    @Override
    public void onStart() {
        super.onStart();

        onRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.inject(this, mLayout);
        mLayout.setOnRefreshListener(this);

        mAdapter = new UserListAdapter(getActivity(), false);
        mUserList.setAdapter(mAdapter);

        mUserList.setOnItemClickListener(this);

        onRefresh();

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
            startActivity(intent);
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
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Context context = getActivity();
        Long uid = RuntimeVar.uid;

        mUserAPI.listFriend(uid, new BaseCallback<List<UserAO>>(context) {
            @Override
            public void call(List<UserAO> result) {
                mLayout.setRefreshing(false);
                mAdapter.setDataset(result);
            }
        });
    }
}
