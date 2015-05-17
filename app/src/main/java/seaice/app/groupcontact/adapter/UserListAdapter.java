package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.view.TableAdapter;

/**
 * The adapter provides data for a list view of user.
 *
 * @author zhb
 */
public class UserListAdapter extends TableAdapter {

    private List<UserAO> mDataSet;

    private boolean mIsFromGroup;

    public UserListAdapter(Context context, boolean isFromGroup) {
        super(context);

        mDataSet = new ArrayList<>();
        mIsFromGroup = isFromGroup;
    }

    public void setDataSet(List<UserAO> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public List<Object> getDataSet() {
        List<Object> dataSet = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); ++i) {
            dataSet.add(mDataSet.get(i));
        }
        return dataSet;
    }

    @Override
    public View getFooter() {
        View rootView;
        if (!mIsFromGroup) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.item_dataset_count, null);
            TextView dataCount = (TextView) rootView.findViewById(R.id.dataCount);
            dataCount.setText("总共" + mDataSet.size() + "位好友");
        } else {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.item_leave, null);
            Button button = (Button) rootView.findViewById(R.id.action_leave_group);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((UserListActivity) mContext).leaveGroup();
                }
            });
        }
        return rootView;
    }

    @Override
    public int getRowCount(int section) {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public View getRow(int section, int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
        UserAO userAO = mDataSet.get(row);

        TextView vNameView = (TextView) rootView.findViewById(R.id.userName);
        vNameView.setText(userAO.getName());
        TextView vPhoneView = (TextView) rootView.findViewById(R.id.userPhone);
        vPhoneView.setText(userAO.getPhone());

        return rootView;
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyDataSetChanged();
    }
}
