package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * The adapter provides data for a list view of user.
 *
 * @author zhb
 */
public class UserListAdapter extends BaseAdapter {

    private Context mContext;

    private List<UserAO> mDataset;

    private boolean mIsFromGroup;

    public UserListAdapter(Context context, boolean isFromGroup) {
        mContext = context;
        mDataset = new ArrayList<>();
        mIsFromGroup = isFromGroup;
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
        if (mIsFromGroup) {
            return mDataset.size() + 1;
        }
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
        View rootView = null;

        if (position == mDataset.size()) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.item_leave, null);
            Button button = (Button) rootView.findViewById(R.id.action_leave_group);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((UserListActivity) mContext).leaveGroup();
                }
            });
            return rootView;
        }

        rootView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
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
