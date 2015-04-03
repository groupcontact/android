package seaice.app.groupcontact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.UserListAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * The Screen Shows the listGroup of users in the specified group.
 *
 * @author zhb
 */
public class UserListActivity extends BaseActivity {

    @Inject
    GroupAPI mGroupAPI;

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.userList)
    ListView mUserList;

    private UserListAdapter mAdapter;

    private Long mGid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friend_list);

        ButterKnife.inject(this);

        // enable home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdapter = new UserListAdapter(this);
        mUserList.setAdapter(mAdapter);

        mGid = getIntent().getLongExtra("gid", -1L);
        setTitle(getIntent().getStringExtra("name"));

        final Context context = this;

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.loading_user_list));
        dialog.setCancelable(false);
        dialog.show();

        String accessToken = Constants.accessTokens.get(mGid);
        mGroupAPI.list(mGid, accessToken, new BaseCallback<List<UserAO>>(this) {
            @Override
            public void call(List<UserAO> result) {
                mAdapter.setDataset(result);
                dialog.dismiss();
            }
        });

        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserAO user = (UserAO) parent.getAdapter().getItem(position);
                Intent intent = new Intent(context, UserInfoActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        mUserList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(R.string.user_operation);
                menu.add(0, 0, 0, R.string.user_operation_call);
                menu.add(0, 0, 1, R.string.user_operation_sms);
                menu.add(0, 0, 2, R.string.add_to_friend);
            }
        });
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
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
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
            // add friend
            mUserAPI.addFriend(Constants.uid, user.getName(), user.getPhone(), new BaseCallback<GeneralAO>(this) {
                @Override
                public void call(GeneralAO result) {
                    if (result.getStatus() == 0L) {
                        info(getString(R.string.success_add_friend));
                    } else {
                        info(result.getInfo());
                    }
                }
            });
        }
        return super.onContextItemSelected(item);
    }
}
