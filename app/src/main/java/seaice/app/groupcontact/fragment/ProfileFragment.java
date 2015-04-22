package seaice.app.groupcontact.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.groupcontact.RuntimeVar;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * User Profile Info Edit And Save.
 *
 * @author zhb
 */
public class ProfileFragment extends BaseFragment {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.editName)
    EditText mNameView;

    @InjectView(R.id.editPhone)
    EditText mPhoneView;

    @InjectView(R.id.editEmail)
    EditText mEmailView;

    @InjectView(R.id.editWechat)
    EditText mWechatView;

    private Context mContext;

    private Long mUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, rootView);

        mUid = RuntimeVar.uid;
        mContext = getActivity();

        // find user
        mUserAPI.find(mUid, new BaseCallback<List<UserAO>>(mContext) {
            @Override
            public void call(List<UserAO> result) {
                // some internal info happened
                if (result.size() == 0) {
                    return;
                }
                UserAO user = result.get(0);
                mNameView.setText(user.getName());
                mPhoneView.setText(user.getPhone());
                try {
                    JSONObject extObj = new JSONObject(user.getExt());
                    mEmailView.setText(extObj.optString("email", ""));
                    mWechatView.setText(extObj.optString("wechat", ""));
                } catch (JSONException e) {
                    // ignore this;
                }
            }
        });
        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset_password) {
            resetPassword();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.action_save_user)
    public void save() {
        UserAO user = new UserAO();
        user.setUid(mUid);
        user.setName(mNameView.getText().toString());
        user.setPhone(mPhoneView.getText().toString());
        JSONObject extObj = new JSONObject();
        try {
            extObj.put("email", mEmailView.getText().toString());
            extObj.put("wechat", mWechatView.getText().toString());
            user.setExt(extObj.toString());
        } catch (JSONException e) {
            user.setExt("{}");
        }
        mUserAPI.save(user, RuntimeVar.password, new BaseCallback<GeneralAO>(mContext) {
            @Override
            public void call(GeneralAO result) {
                if (result.getStatus() == 1) {
                    Toast.makeText(mContext, mContext.getResources().getText(
                            R.string.success_save_user), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, mContext.getResources().getText(
                            R.string.fail_save_user), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void resetPassword() {
        // Ask the user to enter the accessToken
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.new_password));
        LinearLayout container = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_reset_password, null);
        final EditText newPass = (EditText) container.findViewById(R.id.enter_new_password);
        builder.setView(container);

        builder.setPositiveButton(getResources().getString(R.string.reset_password), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String newPassword = newPass.getText().toString();
                mUserAPI.setPassword(RuntimeVar.uid, RuntimeVar.password, newPassword, new BaseCallback<GeneralAO>(getActivity()) {
                    @Override
                    public void call(GeneralAO result) {
                        if (result.getStatus() == 1) {
                            info(getString(R.string.success_reset_password));
                            RuntimeVar.password = newPassword;
                            getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                                    .putString("password", newPassword).apply();
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
