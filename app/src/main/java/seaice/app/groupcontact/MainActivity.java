package seaice.app.groupcontact;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.MainPagerAdapter;
import seaice.app.groupcontact.view.NavBarView;
import seaice.app.groupcontact.view.TabBarView;

/**
 * The First Screen In Action: Three Tabs(Groups, Friends, Profile)
 *
 * @author zhb
 */
public class MainActivity extends FragmentActivity {

    // Support the press again to exit functionality..
    private static final int RESET_BACK_COUNT = 123456;
    private static Handler mHandler = new Handler();

    @InjectView(R.id.pager)
    ViewPager mPager;
    @InjectView(R.id.tabBar)
    TabBarView mTabBarView;
    @InjectView(R.id.navBar)
    NavBarView mNavBarView;
    private int mBackCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        final String[] titles = getResources().getStringArray(R.array.pager_titles);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.setTitles(titles);

        mPager.setAdapter(adapter);
        mTabBarView.setViewPager(mPager);

        mTabBarView.setOnTabChangeListener(new TabBarView.OnTabChangeListener() {
            @Override
            public void onTabChange(int from, int to) {
                mNavBarView.setTitle(titles[to]);
            }
        });
        mNavBarView.setTitle(titles[0]);
        mNavBarView.addRightBarItem(R.mipmap.ic_action_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // there are three pages in totally, so just increase the size for performance
        mPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onBackPressed() {
        ++mBackCount;
        // the first press, toast the tip
        if (mBackCount == 1) {
            Toast.makeText(this,
                    getResources().getString(R.string.tip_press_to_exit),
                    Toast.LENGTH_SHORT).show();
            // after 3s, then reset the back count
            mHandler.sendEmptyMessage(RESET_BACK_COUNT);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBackCount = 0;
                }
            }, 3000);
        } else {
            // the second press, exit application
            super.onBackPressed();
        }
    }
}
