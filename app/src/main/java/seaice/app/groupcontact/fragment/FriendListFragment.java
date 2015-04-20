package seaice.app.groupcontact.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
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
import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserInfoActivity;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

public class FriendListFragment extends BaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    ListView mUserList;

    private UserListAdapter mAdapter;

    private SwipeRefreshLayout mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.inject(this, mLayout);
        mLayout.setOnRefreshListener(this);

        mAdapter = new UserListAdapter(getActivity());
        mUserList.setAdapter(mAdapter);

        mUserList.setOnItemClickListener(this);
        mUserList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(R.string.user_operation);
                menu.add(0, 0, 0, R.string.user_operation_call);
                menu.add(0, 0, 1, R.string.user_operation_sms);
                menu.add(0, 0, 2, R.string.delete_friend);
            }
        });

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
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        UserAO user = (UserAO) mAdapter.getItem(info.position);
        if (item.getOrder() == 0) {
            // call
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + user.getPhone()));
            startActivity(intent);
        }
        if (item.getOrder() == 1) {
            // sms
            Uri uri = Uri.parse("smsto:" + user.getPhone());
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(intent);
        }
        if (item.getOrder() == 2) {
            mUserAPI.deleteFriend(Constants.uid, Constants.name, user.getUid(), new BaseCallback<GeneralAO>(getActivity()) {
                @Override
                public void call(GeneralAO result) {
                    if (result.getStatus() == 0) {
                        info(getString(R.string.success_delete_friend));
                        // delete from list view
                        mAdapter.remove(info.position);
                    } else {
                        info(result.getInfo());
                    }
                }
            });
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Context context = getActivity();
        Long uid = Constants.uid;

        mUserAPI.listFriend(uid, new BaseCallback<List<UserAO>>(context) {
            @Override
            public void call(List<UserAO> result) {
                mLayout.setRefreshing(false);
                mAdapter.setDataset(result);
            }
        });
    }
}
