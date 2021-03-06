package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import seaice.app.appbase.BaseApplication;

public abstract class BaseFragment extends Fragment {

    private String mTitle;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        if (args != null) {
            mTitle = getArguments().getString("title");
        }

        if (needDagger()) {
            BaseApplication.inject(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public String[] getStringArray(int id) {
        return getActivity().getResources().getStringArray(id);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent.getStringExtra("title") == null) {
            intent.putExtra("title", getTitle());
        }
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivity(Intent intent) {
        if (intent.getStringExtra("title") == null) {
            intent.putExtra("title", getTitle());
        }
        super.startActivity(intent);
    }

    public String getTitle() {
        return mTitle;
    }

    /* 是否需要Dagger做Member Injection */
    public boolean needDagger() {
        return true;
    }
}
