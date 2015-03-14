package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import seaice.app.groupcontact.MyApplication;

public class DaggerFragment extends Fragment {

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MyApplication.inject(this);
    }
}
