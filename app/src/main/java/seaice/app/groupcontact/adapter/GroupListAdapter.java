package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import seaice.app.appbase.adapter.BaseTableAdapter;
import seaice.app.appbase.view.SearchBarView;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.GroupAO;

public class GroupListAdapter extends BaseTableAdapter {

    private List<GroupAO> mDataSet;

    /* 点击搜索栏的动作 */
    private View.OnClickListener mListener;

    public GroupListAdapter(Context context, View.OnClickListener listener) {
        super(context);
        mDataSet = new ArrayList<>();

        mListener = listener;
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
    public View getHeader(ViewGroup parent) {
        if (mListener == null) {
            return null;
        }
        SearchBarView searchBarView = (SearchBarView) LayoutInflater.from(mContext).inflate(
                R.layout.item_group_searchbar, parent, false);
        searchBarView.setOnClickListener(mListener);
        return searchBarView;
    }

    @Override
    public boolean hasHeader() {
        return mListener != null;
    }

    @Override
    public View getFooter(ViewGroup parent) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_dataset_count,
                parent, false);
        TextView dataCount = (TextView) rootView.findViewById(R.id.dataCount);
        dataCount.setText("总共" + mDataSet.size() + "个群组");
        return rootView;
    }

    @Override
    public boolean hasFooter() {
        return true;
    }

    public boolean isEnabled(int position) {
        return position == 0 || super.isEnabled(position);
    }

    @Override
    public View getRow(int section, int row, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mAvatarView = (ImageView) convertView.findViewById(R.id.groupAvatar);
            viewHolder.mNameView = (TextView) convertView.findViewById(R.id.groupName);
            viewHolder.mDescView = (TextView) convertView.findViewById(R.id.groupDesc);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GroupAO groupAO = mDataSet.get(row);
        String name = groupAO.getName();

        viewHolder.mAvatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(name.substring(0, 1),
                        ColorGenerator.MATERIAL.getColor(name), 0));
        viewHolder.mNameView.setText(name);
        viewHolder.mDescView.setText(groupAO.getDesc());

        return convertView;
    }

    @Override
    public List<Object> getDataSet() {
        List<Object> dataSet = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); ++i) {
            dataSet.add(mDataSet.get(i));
        }
        return dataSet;
    }

    private static class ViewHolder {
        ImageView mAvatarView;
        TextView mNameView;
        TextView mDescView;
    }
}
