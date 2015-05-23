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

public class RegisterFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.phone)
    EditText mPhoneView;

    @InjectView(R.id.password)
    EditText mPasswordView;

    @InjectView(R.id.name)
    EditText mNameView;

    @InjectView(R.id.register)
    ActionProcessButton mRegisterBtn;

    @Inject
    UserAPI mUserAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, null);
        ButterKnife.inject(this, rootView);

        mRegisterBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        mRegisterBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        mRegisterBtn.setProgress(1);

        disableAll();

        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        mUserAPI.register(phone, password, new BaseCallback<GeneralAO>(getActivity()) {
            @Override
            public void call(GeneralAO result) {
                // 网络错误
                if (result == null) {
                    info(getString(R.string.error_network));
                    onError();
                    return;
                }
                // 成功登录
                if (result.getStatus() == 2) {
                    onSuccess(result.getId());
                }
                // 还需要保存名字信息
                else if (result.getStatus() == 1) {
                    // 保存名字信息
                    onSave(result.getId());
                } else {
                    // 注册失败
                    info(result.getInfo());
                    mRegisterBtn.setProgress(-1);
                    enableAll();
                }
            }
        });
    }

    private void onError() {
        mRegisterBtn.setProgress(0);
        enableAll();
    }

    private void onSave(final long uid) {
        UserAO user = new UserAO();
        user.setUid(uid);
        user.setName(mNameView.getText().toString());
        user.setPhone(mPhoneView.getText().toString());
        user.setExt("{}");

        mUserAPI.save(user, mPasswordView.getText().toString(), new BaseCallback<GeneralAO>(getActivity()) {
            @Override
            public void call(GeneralAO result) {
                if (result == null) {
                    info(getString(R.string.error_network));
                    onError();
                    return;
                }
                if (result.getStatus() == 1) {
                    onSuccess(uid);
                } else {
                    // 保存信息失败
                    info(result.getInfo());
                    mRegisterBtn.setProgress(-1);
                    enableAll();
                }
            }
        });
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
                mRegisterBtn.setProgress(100);
                ((AuthActivity) getActivity()).setUser(result.get(0),
                        mPasswordView.getText().toString());
            }
        });
    }

    private void enableAll() {
        mPhoneView.setEnabled(true);
        mPasswordView.setEnabled(true);
        mNameView.setEnabled(true);
        mRegisterBtn.setEnabled(true);
    }

    private void disableAll() {
        mPhoneView.setEnabled(false);
        mPasswordView.setEnabled(false);
        mNameView.setEnabled(false);
        mRegisterBtn.setEnabled(false);
    }
}
