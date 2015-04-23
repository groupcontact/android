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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.GroupCreateActivity;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.RuntimeVar;
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GroupAO;

public class GroupListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int CREATE_GROUP = 1;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.groupList)
    ListView mGroupList;

    private GroupListAdapter mAdapter;

    private SwipeRefreshLayout mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_group_list,
                container, false);
        ButterKnife.inject(this, mLayout);

        final Context context = getActivity();
        mGroupList.setAdapter(mAdapter);

        onRefresh();

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

        mLayout.setOnRefreshListener(this);

        return mLayout;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_group_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_create_group) {
            Intent intent = new Intent(getActivity(), GroupCreateActivity.class);
            startActivityForResult(intent, CREATE_GROUP);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        final Context context = getActivity();
        Long uid = RuntimeVar.uid;
        mUserAPI.listGroup(uid, new BaseCallback<List<GroupAO>>(context) {
            @Override
            public void call(List<GroupAO> result) {
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

        if (requestCode == CREATE_GROUP) {
            if (resultCode == Activity.RESULT_OK) {
                onRefresh();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        mAdapter = new GroupListAdapter(activity);
        super.onAttach(activity);
    }

    @Override
    public String getUnderlyingData() throws Exception {
        List<GroupAO> groups = mAdapter.getDataset();
        JSONArray groupsJSONArray = new JSONArray();
        for (GroupAO group : groups) {
            groupsJSONArray.put(group.toJSON());
        }
        return groupsJSONArray.toString();
    }

    @Override
    public void setUnderlyingData(String data) throws Exception {
        List<GroupAO> groups = new ArrayList<GroupAO>();
        JSONArray groupsJSONArray = new JSONArray(data);
        for (int i = 0; i < groupsJSONArray.length(); i++) {
            groups.add(GroupAO.parse(groupsJSONArray.getJSONObject(i)));
        }
        mAdapter.setDataset(groups);
    }

    @Override
    public String getUnderlyingPath() {
        return getString(R.string.group_storage);
    }

    /*@Override
    public void storeUnderlyingData() {
        try {
            FileOutputStream outputStream = getActivity().openFileOutput(getString(R.string.group_storage), Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(outputStream));
            List<GroupAO> groups = mAdapter.getDataset();
            JSONArray groupsJSONArray = new JSONArray();
            for(GroupAO group : groups) {
                groupsJSONArray.put(group.toJSON());
            }
            output.write(groupsJSONArray.toString());
            output.flush();
            output.close();
        } catch (IOException | JSONException e) {
            Toast.makeText(getActivity(),getString(R.string.storage_failure),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadUnderlyingData() {
        try {
            FileInputStream inputStream = getActivity().openFileInput(getString(R.string.group_storage));
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            List<GroupAO> groups = new ArrayList<GroupAO>();
            StringBuilder content = new StringBuilder();
            String line = null;
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            JSONArray groupsJSONArray = new JSONArray(content.toString());
            for (int i = 0; i < groupsJSONArray.length(); i++) {
                groups.add(GroupAO.parse(groupsJSONArray.getJSONObject(i)));
            }
            mAdapter.setDataset(groups);
            input.close();
        } catch (FileNotFoundException e) {
            mAdapter.setDataset(new ArrayList<GroupAO>());
        } catch (IOException | JSONException e) {
            mAdapter.setDataset(new ArrayList<GroupAO>());
            Toast.makeText(getActivity(),getString(R.string.storage_load_failure),Toast.LENGTH_SHORT).show();
        }
    }*/
}
