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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.RuntimeVar;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserInfoActivity;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.UserAO;

public class FriendListFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final int MANAGE_FRIEND = 2;

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
        Long uid = RuntimeVar.uid;

        mUserAPI.listFriend(uid, new BaseCallback<List<UserAO>>(context) {
            @Override
            public void call(List<UserAO> result) {
                // if there is no internet access, we should keep the current information by making no change
                if (result == null) {
                    return;
                }

                mLayout.setRefreshing(false);
                mAdapter.setDataset(result);
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

    @Override
    public void onAttach(Activity activity) {
        mAdapter = new UserListAdapter(getActivity(), false);
        super.onAttach(activity);
    }

    @Override
    public String getUnderlyingData() throws Exception {
        List<UserAO> users = mAdapter.getDataset();
        JSONArray usersJSONArray = new JSONArray();
        for (UserAO user : users) {
            usersJSONArray.put(user.toJSON());
        }
        return usersJSONArray.toString();
    }

    @Override
    public void setUnderlyingData(String data) throws Exception {
        List<UserAO> users = new ArrayList<UserAO>();
        JSONArray usersJSONArray = new JSONArray(data);
        for (int i = 0; i < usersJSONArray.length(); i++) {
            users.add(UserAO.parse(usersJSONArray.getJSONObject(i)));
        }
        mAdapter.setDataset(users);
    }

    @Override
    public String getUnderlyingPath() {
        return getString(R.string.friend_storage);
    }
}
