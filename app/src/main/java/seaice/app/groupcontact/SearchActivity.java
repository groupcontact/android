package seaice.app.groupcontact;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import seaice.app.appbase.BaseActivity;
import seaice.app.appbase.view.AlertView;
import seaice.app.appbase.view.ProgressView;
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

    @InjectView(R.id.groupList)
    ListView mGroupList;

    @InjectView(R.id.searchKey)
    EditText mSearchKeyView;

    @InjectView(R.id.cancel)
    TextView mCancelView;

    InputMethodManager mImm;

    ProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GroupListAdapter adapter = new GroupListAdapter(this, null);
        mGroupList.setAdapter(adapter);
        mGroupList.setOnItemClickListener(this);

        final Context context = this;

        mSearchKeyView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ingored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = s.toString().trim();
                if (key.equals("")) {
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

        mImm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSearchKeyView.requestFocus()) {
            mImm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
        final GroupAO group = (GroupAO) parent.getAdapter().getItem(position);
        final Context context = this;

        LinearLayout container = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_password, null);
        final EditText vAccessToken = (EditText) container.findViewById(R.id.enter_password);

        // Ask the user to enter the accessToken
        AlertView.Builder builder = AlertView.Builder.with(this).title(group.getName())
                .message(R.string.enter_access_token)
                .content(container);
        builder.positive(R.string.join_group, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mProgressView = ProgressView.show(SearchActivity.this, getString(R.string.joining_group), true, null);
                final String atNew = vAccessToken.getText().toString();
                mUserAPI.joinGroup(Var.uid, Var.password, group.getGid(), atNew, new BaseCallback<GeneralAO>(context) {
                    @Override
                    public void call(GeneralAO result) {
                        if (mProgressView != null) {
                            mProgressView.dismiss();
                        }
                        if (result.getStatus() == 1) {
                            info(context.getString(R.string.success_join_group));
                        } else {
                            info(result.getInfo());
                        }
                    }
                });
            }
        });

        builder.negative(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    public void onPause() {
        super.onPause();

        overridePendingTransition(0, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean hasNavBar() {
        return false;
    }

    @Override
    protected boolean needSwipeBack() {
        return false;
    }
}

