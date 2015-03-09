package seaice.app.groupcontact.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import seaice.app.groupcontact.R;

/**
 * TODO: document your custom view class.
 */
public class PagerTabView extends ViewGroup {

    private int mColor = Color.BLACK;
    private int mPressedColor = Color.GREEN;

    private ViewPager mPager;

    public PagerTabView(Context context) {
        super(context);
        init(null, 0);
    }

    public PagerTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PagerTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PagerTabView, defStyle, 0);

        a.recycle();

    }

    /**
     * Set the ViewPager
     *
     * @param pager
     */
    public void setViewPager(ViewPager pager) {
        mPager = pager;

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void addTab(int textResId, int imgResId) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
