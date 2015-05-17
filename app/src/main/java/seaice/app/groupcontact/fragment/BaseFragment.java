package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import seaice.app.groupcontact.MyApplication;

public abstract class BaseFragment extends Fragment {

    private String mTitle;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mTitle = getArguments().getString("title");

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
}
