package seaice.app.groupcontact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.adapter.GroupListAdapter;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.impl.GroupAPImpl;


public class SearchActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private GroupAPI mGroupAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText vSearchKey = (EditText) findViewById(R.id.searchKey);
        ListView vSearchResult = (ListView) findViewById(R.id.searchResult);
        final GroupListAdapter adapter = new GroupListAdapter(this);
        vSearchResult.setAdapter(adapter);

        final Context context = this;
        mGroupAPI = new GroupAPImpl(context);

        vSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ingored
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = vSearchKey.getText().toString();
                if (key.trim().equals("")) {
                    // do not allowed empty query
                    adapter.setDataset(new ArrayList<GroupAO>());
                    return;
                }
                mGroupAPI.search(key, new Callback<List<GroupAO>>() {
                    @Override
                    public void call(List<GroupAO> result) {
                        adapter.setDataset(result);
                    }

                    @Override
                    public void error(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ignored
            }
        });

        vSearchResult.setOnItemClickListener(this);
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
        final Long uid = prefs.getLong("uid", -1L);

        if (prefs.getString("accessToken_" + id, "").equals("")) {
            // Ask the user to enter the accessToken
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(group.getName());
            final EditText vAccessToken = new EditText(this);
            vAccessToken.setInputType(InputType.TYPE_MASK_CLASS | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            vAccessToken.setHint(getResources().getString(R.string.hint_enter_token));
            builder.setView(vAccessToken);
            builder.setPositiveButton(getResources().getString(R.string.join_group), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    final String accessToken = vAccessToken.getText().toString();
                    mGroupAPI.join(uid, id, accessToken, new Callback<GeneralAO>() {
                        @Override
                        public void call(GeneralAO result) {
                            if (result.getStatus() != 0) {
                                Toast.makeText(context, context.getResources().getString(
                                        R.string.error_join_group), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getResources().getString(
                                        R.string.success_join_group), Toast.LENGTH_LONG).show();
                                prefs.edit().putString("accessToken_" + id, accessToken).commit();
                            }
                        }

                        @Override
                        public void error(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
        } else {
            // Jump to the User List Activity
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("gid", group.getGid());
            intent.putExtra("name", group.getName());
            startActivity(intent);
        }
    }
}
