package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.utils.CipherUtils;

/**
 * The adapter provides data for a list view of user.
 *
 * @author zhb
 */
public class UserListAdapter extends BaseAdapter {

    private Context mContext;

    private List<UserAO> mDataset;

    public UserListAdapter(Context context) {
        mContext = context;
        mDataset = new ArrayList<>();
    }

    public void setDataset(List<UserAO> dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
    }

    public List<UserAO> getDataset() {
        return mDataset;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataset.get(position).getUid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
        UserAO userAO = mDataset.get(position);

        TextView vNameView = (TextView) rootView.findViewById(R.id.userName);
        vNameView.setText(userAO.getName());
        TextView vPhoneView = (TextView) rootView.findViewById(R.id.userPhone);
        vPhoneView.setText(userAO.getPhone());

        return rootView;
    }

    public void remove(int position) {
        mDataset.remove(position);
        notifyDataSetChanged();
    }
}
