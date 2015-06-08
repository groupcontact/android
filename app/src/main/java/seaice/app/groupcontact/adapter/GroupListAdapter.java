package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import seaice.app.appbase.view.SearchBarView;
import seaice.app.appbase.view.TableAdapter;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.GroupAO;

public class GroupListAdapter extends TableAdapter {

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
    public View getHeader() {
        if (mListener == null) {
            return null;
        }
        SearchBarView searchBarView = (SearchBarView) LayoutInflater.from(mContext).inflate(
                R.layout.item_group_searchbar, null);
        searchBarView.setOnClickListener(mListener);
        return searchBarView;
    }

    @Override
    public View getFooter() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_dataset_count, null);
        TextView dataCount = (TextView) rootView.findViewById(R.id.dataCount);
        dataCount.setText("总共" + mDataSet.size() + "个群组");
        return rootView;
    }

    public boolean isEnabled(int position) {
        /* SearchBar是可点的 */
        if (position == 0) {
            return true;
        } else {
            return super.isEnabled(position);
        }
    }

    @Override
    public View getRow(int section, int row, View convertView) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        GroupAO groupAO = mDataSet.get(row);
        String name = groupAO.getName();

        ImageView vAvatarView = (ImageView) rootView.findViewById(R.id.groupAvatar);
        vAvatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(name.substring(0, 1),
                        ColorGenerator.MATERIAL.getColor(name), 0));
        TextView vNameView = (TextView) rootView.findViewById(R.id.groupName);
        vNameView.setText(name);
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
