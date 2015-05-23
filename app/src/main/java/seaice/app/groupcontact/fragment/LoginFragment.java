package seaice.app.groupcontact.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.AuthActivity;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.BaseCallback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.phone)
    EditText mPhoneView;

    @InjectView(R.id.password)
    EditText mPasswordView;

    @InjectView(R.id.login)
    ActionProcessButton mLoginBtn;

    @Inject
    UserAPI mUserAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, null);
        ButterKnife.inject(this, rootView);

        mLoginBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        mLoginBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        mLoginBtn.setProgress(1);

        disableAll();

        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        mUserAPI.register(phone, password, new BaseCallback<GeneralAO>(getActivity()) {
            @Override
            public void call(GeneralAO result) {
                if (result == null) {
                    info(getString(R.string.error_network));
                    onError();
                    return;
                }
                // 成功登录
                if (result.getStatus() == 2) {
                    onSuccess(result.getId());
                } else {
                    // 登录失败
                    mLoginBtn.setProgress(-1);
                    enableAll();
                }
            }
        });
    }

    private void onError() {
        mLoginBtn.setProgress(0);
        enableAll();
    }

    private void onSuccess(long uid) {
        mUserAPI.find(uid, new BaseCallback<List<UserAO>>(getActivity()) {
            @Override
            public void call(List<UserAO> result) {
                if (result == null || result.size() == 0) {
                    info(getString(R.string.error_network));
                    onError();
                    return;
                }
                mLoginBtn.setProgress(100);
                ((AuthActivity) getActivity()).setUser(result.get(0),
                        mPasswordView.getText().toString());
            }
        });
    }

    private void enableAll() {
        mPhoneView.setEnabled(true);
        mPasswordView.setEnabled(true);
        mLoginBtn.setEnabled(true);
    }

    private void disableAll() {
        mPhoneView.setEnabled(false);
        mPasswordView.setEnabled(false);
        mLoginBtn.setEnabled(false);
    }
}
