package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.view.TableAdapter;

/**
 * The adapter provides data for a list view of user.
 *
 * @author zhb
 */
public class UserListAdapter extends TableAdapter {

    private List<UserAO> mDataSet;

    private List<Character> mSections;

    private Map<Character, List<UserAO>> mSectionedDataSet;

    private boolean mIsFromGroup;

    public UserListAdapter(Context context, boolean isFromGroup) {
        super(context);

        mDataSet = new ArrayList<>();
        mSections = new ArrayList<>();
        mSectionedDataSet = new HashMap<>();
        mIsFromGroup = isFromGroup;
    }

    public void setDataSet(List<UserAO> dataSet) {
        mDataSet = dataSet;
        mSections.clear();
        mSectionedDataSet.clear();
        for (UserAO user : dataSet) {
            Character c = PinyinHelper.getShortPinyin(user.getName()).toUpperCase().charAt(0);
            List<UserAO> subDataSet = mSectionedDataSet.get(c);
            if (subDataSet == null) {
                mSections.add(c);
                subDataSet = new ArrayList<>();
                mSectionedDataSet.put(c, subDataSet);
            }
            subDataSet.add(user);
        }
        notifyDataSetChanged();
    }

    public String getSectionHeader(int section) {
        if (section >= mSections.size()) {
            return null;
        }
        return mSections.get(section).toString();
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

        rootView = LayoutInflater.from(mContext).inflate(R.layout.item_dataset_count, null);
        TextView dataCount = (TextView) rootView.findViewById(R.id.dataCount);
        dataCount.setText("总共" + mDataSet.size() + "位" + (mIsFromGroup ? "成员" : "好友"));

        return rootView;
    }

    @Override
    public int getRowCount(int section) {
        return mSectionedDataSet.get(mSections.get(section)).size();
    }

    @Override
    public View getRow(int section, int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_user, null);
        UserAO userAO = mSectionedDataSet.get(mSections.get(section)).get(row);
        String name = userAO.getName();

        ImageView vAvatarView = (ImageView) rootView.findViewById(R.id.userAvatar);
        vAvatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(name.substring(name.length() - 1),
                        ColorGenerator.MATERIAL.getColor(name), 0));
        TextView vNameView = (TextView) rootView.findViewById(R.id.userName);
        vNameView.setText(userAO.getName());
        TextView vPhoneView = (TextView) rootView.findViewById(R.id.userPhone);
        vPhoneView.setText(userAO.getPhone());

        return rootView;
    }

    @Override
    public int getSectionCount() {
        return mSectionedDataSet.keySet().size();
    }

}
