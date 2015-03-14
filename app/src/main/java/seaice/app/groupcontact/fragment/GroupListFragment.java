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
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GroupAO;

public class GroupListFragment extends DaggerFragment {

    @Inject
    UserAPI mUserAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_group_list, container, false);

        final Context context = getActivity();

        ListView vGroupList = (ListView) rootView.findViewById(R.id.groupList);
        final GroupListAdapter adapter = new GroupListAdapter(context);
        vGroupList.setAdapter(adapter);

        // start a http call to load the group listGroup
        Long uid = context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getLong("uid", -1L);
        mUserAPI.listGroup(uid, new Callback<List<GroupAO>>() {
            @Override
            public void call(List<GroupAO> result) {
                adapter.setDataset(result);
                for (GroupAO group : result) {
                    // Special Case: Initialized Data.
                    if (group.getGid() == 1) {
                        context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                                .putString("accessToken_" + 1, "123456").commit();
                        break;
                    }
                }
            }

            @Override
            public void error(String message) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });

        vGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return super.onOptionsItemSelected(item);
    }
}
