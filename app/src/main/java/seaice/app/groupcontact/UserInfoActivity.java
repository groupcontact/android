package seaice.app.groupcontact;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.InjectView;
import seaice.app.groupcontact.adapter.UserInfoAdapter;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.view.NavBarView;
import seaice.app.groupcontact.view.TableView;


public class UserInfoActivity extends BaseActivity implements TableView.OnCellClickListener,
        View.OnClickListener {

    @InjectView(R.id.navBar)
    NavBarView mNavBarView;

    @InjectView(R.id.filedList)
    TableView mTableView;

    @Inject
    UserAPI mUserAPI;

    UserAO mUser;

    JSONObject mExtAttrs;

    PopupWindow mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = getIntent().getParcelableExtra("user");
        try {
            mExtAttrs = new JSONObject(mUser.getExt());
        } catch (JSONException e) {
            mExtAttrs = new JSONObject();
            Log.e("UserInfoActivity", "Can not parse external attribute to JSONObject");
        }
        setTitle(mUser.getName());
        mNavBarView.setTitle(mUser.getName());
        mNavBarView.setRightItemOnClickListener(this);

        mTableView.setAdapter(new UserInfoAdapter(this, mUser));

        mTableView.setOnCellClickListener(this);

        mNavBarView.setRightActions(-1, new String[]{
                "加为好友", "删除好友"
        }, new int[]{R.drawable.call, R.drawable.message});
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_info;
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
    public void onCellClick(AdapterView<?> parent, View view, int section, int row, long id) {
        if (section == 0 && row == 0) {
            Intent intent = new Intent(this, ActionSheetActivity.class);
            intent.putExtra(ActionSheetActivity.TITLE, mUser.getPhone());
            intent.putExtra(ActionSheetActivity.ACTIONS,
                    getResources().getStringArray(R.array.user_info_phone_actions));
            intent.putExtra(ActionSheetActivity.CANCEL, true);
            startActivityForResult(intent, Let.REQUEST_CODE_OPERATE_PHONE);
        } else if (section == 0 && row == 1) {
            if (mExtAttrs.optString("email", "").equals("")) {
                return;
            }
            Intent intent = new Intent(this, ActionSheetActivity.class);
            intent.putExtra(ActionSheetActivity.TITLE, mExtAttrs.optString("email", ""));
            intent.putExtra(ActionSheetActivity.ACTIONS,
                    getResources().getStringArray(R.array.user_info_email_actions));
            intent.putExtra(ActionSheetActivity.CANCEL, true);
            startActivityForResult(intent, Let.REQUEST_CODE_OPERATE_EMAIL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == Let.REQUEST_CODE_OPERATE_PHONE) {
                int action = data.getIntExtra("ACTION", -1);
                if (action == 0) {
                    call();
                } else if (action == 1) {
                    sendMessage();
                } else if (action == 2) {
                    sendContact();
                } else if (action == 3) {
                    copyToClipboard(mUser.getPhone());
                }
            } else if (requestCode == Let.REQUEST_CODE_OPERATE_EMAIL) {
                int action = data.getIntExtra("ACTION", -1);
                if (action == 0) {
                    sendEmail();
                } else if (action == 1) {
                    copyToClipboard(mExtAttrs.optString("email", ""));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + mUser.getPhone()));
        startActivity(intent);
    }

    public void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + mUser.getPhone()));
        startActivity(intent);
    }

    public void sendContact() {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.putExtra("sms_body", mUser.getName() + ": " + mUser.getPhone());
        it.setType("vnd.android-dir/mms-sms");
        startActivity(it);
    }

    public void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("message", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, R.string.success_copy, Toast.LENGTH_SHORT).show();
    }

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                mExtAttrs == null ? "" : mExtAttrs.optString("email", "")
        });
        intent.setType("message/rfc822");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (!mPopupMenu.isShowing()) {
            mPopupMenu.showAsDropDown(v);
        } else {
            mPopupMenu.dismiss();
        }
    }
//
//    public void addFriend() {
//        mUserAPI.addFriend(Var.uid, Var.password, mUser.getName(), mUser.getPhone(),
//                new BaseCallback<GeneralAO>(this) {
//                    public void call(GeneralAO result) {
//                        if (result == null) {
//                            info(getString(R.string.error_network));
//                            return;
//                        }
//                        if (result.getStatus() == 1) {
//                            info(getString(R.string.success_add_friend));
//                            finish();
//                        } else {
//                            info(result.getInfo());
//                        }
//                    }
//                });
//    }
//
//    public void deleteFriend() {
//        mUserAPI.deleteFriend(Var.uid, Var.password, mUser.getUid(),
//                new BaseCallback<GeneralAO>(this) {
//                    public void call(GeneralAO result) {
//                        if (result == null) {
//                            info(getString(R.string.error_network));
//                            return;
//                        }
//                        if (result.getStatus() == 1) {
//                            info(getString(R.string.success_delete_friend));
//                            Intent returnIntent = new Intent();
//                            setResult(RESULT_OK, returnIntent);
//                            finish();
//                        } else {
//                            info(result.getInfo());
//                        }
//                    }
//                });
//    }
}
