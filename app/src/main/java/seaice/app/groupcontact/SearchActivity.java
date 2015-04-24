package seaice.app.groupcontact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
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


public class SearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Inject
    GroupAPI mGroupAPI;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.searchKey)
    EditText mSearchKey;

    @InjectView(R.id.searchResult)
    ListView mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final GroupListAdapter adapter = new GroupListAdapter(this);
        mSearchResult.setAdapter(adapter);

        final Context context = this;

        mSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ingored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = mSearchKey.getText().toString().trim();
                if (key.equals("")) {
                    // do not allowed empty query
                    adapter.setDataset(new ArrayList<GroupAO>());
                    return;
                }
                mGroupAPI.search(key, new BaseCallback<List<GroupAO>>(context) {
                    @Override
                    public void call(List<GroupAO> result) {
                        adapter.setDataset(result);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ignored
            }
        });

        mSearchResult.setOnItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        GroupAO group = (GroupAO) parent.getAdapter().getItem(position);

        final Context context = this;

        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final Long uid = Var.uid;

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
                mUserAPI.joinGroup(Var.uid, Var.password, id, atNew, new BaseCallback<GeneralAO>(context) {
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
}

