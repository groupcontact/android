package seaice.app.groupcontact.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GroupAO;

public class GroupListFragment extends BaseFragment {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.groupList)
    ListView mGroupList;

    private GroupListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.inject(this, rootView);

        final Context context = getActivity();
        mAdapter = new GroupListAdapter(context);
        mGroupList.setAdapter(mAdapter);

        listGroup();

        mGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // here the id is the group id, and here we jumps to the UserListActivity
                Intent intent = new Intent(context, UserListActivity.class);
                GroupAO group = (GroupAO) parent.getAdapter().getItem(position);
                intent.putExtra("gid", group.getGid());
                intent.putExtra("name", group.getName());
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_group_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_refresh_group) {
            listGroup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listGroup() {
        final Context context = getActivity();
        Long uid = Constants.uid;
        mUserAPI.listGroup(uid, new BaseCallback<List<GroupAO>>(context) {
            @Override
            public void call(List<GroupAO> result) {
                mAdapter.setDataset(result);
                for (GroupAO group : result) {
                    // Special Case: Initialized Data.
                    if (group.getGid() == 1) {
                        context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                                .putString("accessToken_" + 1, "123456").apply();
                        Constants.accessTokens.put(1L, "123456");
                        break;
                    }
                }
            }
        });
    }
}
