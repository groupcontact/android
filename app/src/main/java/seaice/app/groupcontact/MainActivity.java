package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import butterknife.InjectView;
import seaice.app.groupcontact.adapter.MainPagerAdapter;
import seaice.app.groupcontact.view.NavBarView;
import seaice.app.groupcontact.view.TabBarView;

/**
 * The First Screen In Action: Three Tabs(Groups, Friends, Profile)
 *
 * @author zhb
 */
public class MainActivity extends BaseActivity implements TabBarView.OnTabChangeListener {

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

    private boolean mNavBarHidden = false;

    String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitles = getResources().getStringArray(R.array.pager_titles);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.setTitles(mTitles);

        mPager.setAdapter(adapter);
        mTabBarView.setViewPager(mPager);

        mTabBarView.setOnTabChangeListener(this);
        mNavBarView.setTitle(mTitles[0]);
        mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });

        // there are three pages in totally, so just increase the size for performance
        mPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onBackPressed() {
        if (mNavBarView.isPopupShowing()) {
            mNavBarView.dismissPopup();
            return;
        }
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

    @Override
    public void onTabChange(int from, int to) {
        mNavBarView.setTitle(mTitles[to]);

        if (to == 0) {
            mNavBarView.setRightItem(getString(R.string.action_add));
            mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFriend();
                }
            });
        } else if (to == 1) {
            mNavBarView.setRightItem(getString(R.string.action_create));
            mNavBarView.setRightItemOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createGroup();
                }
            });
        } else {
            mNavBarView.removeRightItem();
        }
    }

    private void addFriend() {
        Intent intent = new Intent(this, UserAddActivity.class);
        intent.putExtra("title", mTitles[0]);
        startActivityForResult(intent, Let.REQUEST_CODE_ADD_FRIEND);
    }

    private void createGroup() {
        Intent intent = new Intent(this, GroupCreateActivity.class);
        intent.putExtra("title", mTitles[1]);
        startActivityForResult(intent, Let.REQUEST_CODE_CREATE_GROUP);
        ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Let.REQUEST_CODE_ADD_FRIEND) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void animate2Activity(final Class<?> activityClass) {
        mNavBarHidden = true;
        mNavBarView.hide(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, activityClass);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void onResume() {
        super.onResume();
        if (mNavBarHidden) {
            mNavBarView.show(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mNavBarHidden = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean needDagger() {
        return false;
    }

}
