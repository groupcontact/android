package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;

/**
 * Created by zhb on 2015/3/11.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;
        mTitles = mContext.getResources().getStringArray(R.array.pager_titles);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new GroupListFragment();
        } else if (position == 1) {
            return new FriendListFragment();
        } else if (position == 2) {
            return new ProfileFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
