package seaice.app.groupcontact.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.UserAO;

public class FriendListFragment extends DaggerFragment {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    ListView mUserList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final Context context = getActivity();
        final Long uid = Constants.uid;
        final String name = Constants.name;

        View rootView = inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.inject(this, rootView);

        final UserListAdapter adapter = new UserListAdapter(context);
        mUserList.setAdapter(adapter);

        mUserAPI.listFriend(uid, name, new BaseCallback<List<UserAO>>(context) {
            @Override
            public void call(List<UserAO> result) {
                adapter.setDataset(result);
            }
        });

        return rootView;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friend_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_friend) {
            // TODO: Add Friend Dialog Or New Activity...
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
