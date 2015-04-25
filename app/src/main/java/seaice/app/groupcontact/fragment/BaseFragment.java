package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;
import seaice.app.groupcontact.MyApplication;
import seaice.app.groupcontact.R;

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
