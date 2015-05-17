package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.view.TableAdapter;

public class GroupListAdapter extends TableAdapter {

    private List<GroupAO> mDataSet;

    public GroupListAdapter(Context context) {
        super(context);
        mDataSet = new ArrayList<>();
    }

    public void setDataSet(List<GroupAO> dataset) {
        mDataSet = dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    @Override
    public int getRowCount(int section) {
        return mDataSet.size();
    }

    @Override
    public View getFooter() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_dataset_count, null);
        TextView dataCount = (TextView) rootView.findViewById(R.id.dataCount);
        dataCount.setText("总共" + mDataSet.size() + "个群组");
        return rootView;
    }

    @Override
    public View getRow(int section, int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        GroupAO groupAO = mDataSet.get(row);

        TextView vNameView = (TextView) rootView.findViewById(R.id.groupName);
        vNameView.setText(groupAO.getName());
        TextView vDescView = (TextView) rootView.findViewById(R.id.groupDesc);
        vDescView.setText(groupAO.getDesc());

        return rootView;
    }

    @Override
    public List<Object> getDataSet() {
        List<Object> dataSet = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); ++i) {
            dataSet.add(mDataSet.get(i));
        }
        return dataSet;
    }
}
