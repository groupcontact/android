package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.appbase.view.TableView;
import seaice.app.groupcontact.Let;
import seaice.app.groupcontact.MainActivity;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.Var;
import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.utils.FileUtils;

public class GroupListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.groupList)
    TableView mGroupList;

    private GroupListAdapter mAdapter;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_list,
                container, false);

        ButterKnife.inject(this, rootView);

        mAdapter = new GroupListAdapter(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转至搜索Activity
                ((MainActivity) getActivity()).animate2Activity(SearchActivity.class);
            }
        });
        List<GroupAO> dataset = FileUtils.read(getActivity(), Let.GROUP_CACHE_PATH, GroupAO.class);
        // No Cache
        if (dataset == null || dataset.size() == 0) {
            onRefresh();
        } else {
            mAdapter.setDataSet(dataset);
        }
        mGroupList.setAdapter(mAdapter);

        mGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                GroupAO group = (GroupAO) parent.getAdapter().getItem(position);
                intent.putExtra("gid", group.getGid());
                intent.putExtra("name", group.getName());
                startActivityForResult(intent, Let.REQUEST_CODE_VIEW_GROUP);
            }
        });

        mLayout.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onRefresh() {
        final Context context = getActivity();
        Long uid = Var.uid;
        mUserAPI.listGroup(uid, new BaseCallback<List<GroupAO>>(context) {
            @Override
            public void call(List<GroupAO> result) {
                if (result == null) {
                    // 如果是第一次请求数据，则直接从本地读取
                    if (mAdapter.getCount() == 0) {
                        mAdapter.setDataSet(FileUtils.read(getActivity(), Let.GROUP_CACHE_PATH,
                                GroupAO.class));
                    }
                    // 用户想刷新，弹出提示
                    else {
                        info(getString(R.string.error_network));
                    }
                } else {
                    FileUtils.write(getActivity(), Let.GROUP_CACHE_PATH, result, GroupAO.class,
                            result.size() != mAdapter.getCount());
                    mAdapter.setDataSet(result);
                }
                mLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Let.REQUEST_CODE_VIEW_GROUP) {
            if (resultCode == Activity.RESULT_OK) {
                onRefresh();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Var.groupDataChanged) {
            onRefresh();
            Var.groupDataChanged = false;
        }
    }
}
