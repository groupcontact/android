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
import seaice.app.groupcontact.api.ao.GroupAO;

/**
 * Created by zhb on 2015/3/11.
 */
public class GroupListAdapter extends BaseAdapter {

    private Context mContext;

    private List<GroupAO> mDataset;

    public GroupListAdapter(Context context) {
        mContext = context;
        mDataset = new ArrayList<GroupAO>();
    }

    public void setDataset(List<GroupAO> dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
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
        return mDataset.get(position).getGid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        GroupAO groupAO = mDataset.get(position);

        TextView vNameView = (TextView) rootView.findViewById(R.id.groupName);
        vNameView.setText(groupAO.getName());
        TextView vDescView = (TextView) rootView.findViewById(R.id.groupDesc);
        vDescView.setText(groupAO.getDesc());

        return rootView;
    }
}
