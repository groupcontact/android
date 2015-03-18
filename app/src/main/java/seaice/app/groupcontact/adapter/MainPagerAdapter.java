package seaice.app.groupcontact.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
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

    public void setTitles(String[] titles) {
        mTitles = titles;
    }
}
