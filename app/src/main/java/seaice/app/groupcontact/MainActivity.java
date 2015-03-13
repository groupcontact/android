package seaice.app.groupcontact;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import seaice.app.groupcontact.adapter.MainPagerAdapter;
import seaice.app.groupcontact.view.PagerSlidingTabStrip;


public class MainActivity extends ActionBarActivity {

    public static final int RESET_BACK_COUNT = 123456;
    private static Handler mHandler = new Handler();
    private int mBackCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MainPagerAdapter(this.getSupportFragmentManager(), this));

        PagerSlidingTabStrip indicator = (PagerSlidingTabStrip) findViewById(R.id.pagerIndicator);
        indicator.setViewPager(pager);

        // three are three pages in totally, so just increase the size for performance
        pager.setOffscreenPageLimit(2);
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
