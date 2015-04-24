package seaice.app.groupcontact.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.ButterKnife;
import seaice.app.groupcontact.MyApplication;
import seaice.app.groupcontact.R;

public abstract class BaseFragment extends Fragment {

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        MyApplication.inject(this);

        // if there is no internet access, we need to start offline from local information
        boolean networkStatus = activity.getIntent().getBooleanExtra("NetworkStatus", false);
        if (!networkStatus) {
            loadUnderlyingData(getUnderlyingPath());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // store local information for offline start
        storeUnderlyingData(getUnderlyingPath());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * Parse the underlying data which need persisting to a string
     * @return
     * @throws Exception
     */
    public abstract String getUnderlyingData() throws Exception;

    /**
     * Set the underlying data from the string
     * @return
     * @throws Exception
     */
    public abstract void setUnderlyingData(String data) throws Exception;

    /**
     * Get the path for persisting the underlying data
     * @return
     */
    public abstract String getUnderlyingPath();

    public void storeUnderlyingData(String path) {
        try {
            FileOutputStream outputStream = getActivity().openFileOutput(path, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(outputStream));
            String underlyingData = getUnderlyingData();
            output.write(underlyingData);
            output.flush();
            output.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.storage_failure), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadUnderlyingData(String path) {
        try {
            StringBuilder content = new StringBuilder();
            FileInputStream inputStream = getActivity().openFileInput(path);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = input.readLine()) != null) {
                content.append(line);
            }
            setUnderlyingData(content.toString());
            input.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.storage_load_failure), Toast.LENGTH_SHORT).show();
        }
    }
}
