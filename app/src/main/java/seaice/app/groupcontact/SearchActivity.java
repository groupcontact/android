package seaice.app.groupcontact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.view.SearchBarView;


public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Inject
    GroupAPI mGroupAPI;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.groupList)
    ListView mGroupList;

    @InjectView(R.id.searchBar)
    SearchBarView mSearchBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.inject(this);

        final GroupListAdapter adapter = new GroupListAdapter(this, null);
        mGroupList.setAdapter(adapter);
        mGroupList.setOnItemClickListener(this);

        final Context context = this;

        mSearchBarView.setTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ingored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = s.toString().trim();
                if (key.equals("")) {
                    // do not allowed empty query
                    adapter.setDataSet(new ArrayList<GroupAO>());
                    return;
                }
                mGroupAPI.search(key, new BaseCallback<List<GroupAO>>(context) {
                    @Override
                    public void call(List<GroupAO> result) {
                        if (result == null) {
                            info(getString(R.string.error_network));
                            return;
                        }
                        adapter.setDataSet(result);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ignored
            }
        });

        mSearchBarView.enterEditMode();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        final GroupAO group = (GroupAO) parent.getAdapter().getItem(position);
        final Context context = this;

        // Ask the user to enter the accessToken
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(group.getName());
        LinearLayout container = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_password, null);
        final EditText vAccessToken = (EditText) container.findViewById(R.id.enter_password);
        builder.setView(container);

        builder.setPositiveButton(getResources().getString(R.string.join_group), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String atNew = vAccessToken.getText().toString();
                mUserAPI.joinGroup(Var.uid, Var.password, group.getGid(), atNew, new BaseCallback<GeneralAO>(context) {
                    @Override
                    public void call(GeneralAO result) {
                        if (result.getStatus() == 1) {
                            info(context.getString(R.string.success_join_group));
                        } else {
                            info(result.getInfo());
                        }
                    }
                });
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}

