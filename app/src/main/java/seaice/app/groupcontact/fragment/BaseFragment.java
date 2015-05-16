package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import seaice.app.groupcontact.MyApplication;

public abstract class BaseFragment extends Fragment {

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MyApplication.inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public String[] getStringArray(int id) {
        return getActivity().getResources().getStringArray(id);
    }
}
