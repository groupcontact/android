package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.appbase.utils.CipherUtils;
import seaice.app.appbase.view.AlertView;
import seaice.app.appbase.view.ProgressView;
import seaice.app.appbase.view.TableView;
import seaice.app.groupcontact.AuthActivity;
import seaice.app.groupcontact.FeedbackActivity;
import seaice.app.groupcontact.Let;
import seaice.app.groupcontact.QrcodeActivity;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.ScanActivity;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserEditActivity;
import seaice.app.groupcontact.Var;
import seaice.app.groupcontact.adapter.ProfileAdapter;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.FileUtils;

/**
 * User Profile Info Edit And Save.
 *
 * @author zhb
 */
public class ProfileFragment extends BaseFragment {

    @Inject
    UserAPI mUserAPI;

    @InjectView(R.id.profileList)
    TableView mTableView;

    ProgressView mProgressView;

    static final int SCAN_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, rootView);

        mTableView.setAdapter(new ProfileAdapter(getActivity(), getStringArray(R.array.profile_menu_texts)));

        mTableView.setOnCellClickListener(new TableView.OnCellClickListener() {
            @Override
            public void onCellClick(AdapterView<?> parent, View view, int section, int row, long id) {
                if (section == 0) {
                    Intent intent = new Intent(getActivity(), UserEditActivity.class);
                    startActivity(intent);
                    return;
                }
                if (section == 1) {
                    if (row == 0) {
                        Intent intent = new Intent(getActivity(), QrcodeActivity.class);
                        startActivity(intent);
                    } else if (row == 1) {
                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                        startActivityForResult(intent, SCAN_REQUEST_CODE);
                    } else {
//                        FeedbackAgent agent = new FeedbackAgent(getActivity());
//                        agent.startFeedbackActivity();
                        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                        startActivity(intent);
                    }
                }
                if (section == 2) {
                    if (row == 0) {
                        resetPassword();
                    }
                    if (row == 1) {
                        logout();
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                handleScanResult(CipherUtils.decrypt(data.getStringExtra("result"), Let.DEFAULT_KEY));
            }
        }
    }

    private void handleScanResult(String result) {
        if (result == null) {
            Toast.makeText(getActivity(), getString(R.string.error_scan_qrcode), Toast.LENGTH_LONG).show();
        } else {
            String[] tokens = result.split(":");
            Intent intent = new Intent(getActivity(), UserAddActivity.class);
            intent.putExtra("name", tokens[1]);
            intent.putExtra("phone", tokens[2]);
            startActivity(intent);
        }
    }


    private void resetPassword() {
        // Ask the user to enter the accessToken
        AlertView.Builder builder = AlertView.Builder.with(getActivity());
        builder.title(getString(R.string.new_password));
        LinearLayout container = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_password, null);
        final EditText newPass = (EditText) container.findViewById(R.id.enter_password);
        builder.content(container);

        builder.positive(getResources().getString(R.string.reset_password), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.dismiss();
                mProgressView = ProgressView.show(getActivity(),
                        getString(R.string.resettings_password), true, null);
                final String newPassword = newPass.getText().toString();
                mUserAPI.setPassword(Var.uid, Var.password, newPassword, new BaseCallback<GeneralAO>(getActivity()) {
                    @Override
                    public void call(GeneralAO result) {
                        if (mProgressView != null) {
                            mProgressView.dismiss();
                        }
                        if (result.getStatus() == 1) {
                            info(getString(R.string.success_reset_password));
                            Var.password = newPassword;
                            getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                                    .putString("password", newPassword).apply();
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

    private void logout() {
        Var.uid = -1l;
        getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit().putLong("uid", -1).apply();
        FileUtils.write(getActivity(), Let.FRIEND_CACHE_PATH, Collections.<UserAO>emptyList(),
                UserAO.class, true);
        FileUtils.write(getActivity(), Let.GROUP_CACHE_PATH, Collections.<GroupAO>emptyList(),
                GroupAO.class, true);
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
