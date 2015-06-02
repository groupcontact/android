package seaice.app.groupcontact.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;
import seaice.app.groupcontact.view.TabBarAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter implements TabBarAdapter {

    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new FriendListFragment();
        } else if (position == 1) {
            fragment = new GroupListFragment();
        } else {
            fragment = new ProfileFragment();
        }
        Bundle args = new Bundle();
        args.putString("title", mTitles[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public int getIcon(int position) {
        if (position == 0) {
            return R.raw.friend;
        } else if (position == 1) {
            return R.raw.group;
        } else {
            return R.raw.me;
        }
    }

    @Override
    public String getSVGIcon(int position) {
        return null;
    }

    @Override
    public String getTitle(int position) {
        return getPageTitle(position).toString();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }
}
