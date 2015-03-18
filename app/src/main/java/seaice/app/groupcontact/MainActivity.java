package seaice.app.groupcontact;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.MainPagerAdapter;
import seaice.app.groupcontact.view.PagerSlidingTabStrip;

/**
 * The First Screen In Action: Three Tabs(Groups, Friends, Profile)
 *
 * @author zhb
 */
public class MainActivity extends ActionBarActivity {

    // Support the press again to exit functionality..
    private static final int RESET_BACK_COUNT = 123456;
    private static Handler mHandler = new Handler();

    @InjectView(R.id.pager)
    ViewPager mPager;
    @InjectView(R.id.pagerIndicator)
    PagerSlidingTabStrip mIndicator;
    private int mBackCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        MainPagerAdapter adapter = new MainPagerAdapter(this.getSupportFragmentManager());
        adapter.setTitles(getResources().getStringArray(R.array.pager_titles));

        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);

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
