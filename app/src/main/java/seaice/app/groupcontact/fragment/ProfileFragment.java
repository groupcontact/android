package seaice.app.groupcontact.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * User Profile Info Edit And Save.
 *
 * @author zhb
 */
public class ProfileFragment extends DaggerFragment {

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

    private String mName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, rootView);

        mUid = Constants.uid;
        mName = Constants.name;

        mNameView.setText(mName);

        mContext = getActivity();

        // find user
        mUserAPI.find(mUid, mName, new Callback<List<UserAO>>() {
            @Override
            public void call(List<UserAO> result) {
                // some internal error happened
                if (result.size() == 0) {
                    return;
                }
                UserAO user = result.get(0);
                mPhoneView.setText(user.getPhone());
                try {
                    JSONObject extObj = new JSONObject(user.getExt());
                    mEmailView.setText(extObj.optString("email", ""));
                    mWechatView.setText(extObj.optString("wechat", ""));
                } catch (JSONException e) {
                    // ignore this;
                }
            }

            @Override
            public void error(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {
            UserAO user = new UserAO();
            user.setUid(mUid);
            user.setName(mName);
            user.setPhone(mPhoneView.getText().toString());
            JSONObject extObj = new JSONObject();
            try {
                extObj.put("email", mEmailView.getText().toString());
                extObj.put("wechat", mWechatView.getText().toString());
                user.setExt(extObj.toString());
            } catch (JSONException e) {
                user.setExt("{}");
            }
            mUserAPI.edit(user, new Callback<GeneralAO>() {
                @Override
                public void call(GeneralAO result) {
                    if (result.getId() == 0) {
                        Toast.makeText(mContext, mContext.getResources().getText(
                                R.string.success_save_user), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, mContext.getResources().getText(
                                R.string.fail_save_user), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void error(String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}