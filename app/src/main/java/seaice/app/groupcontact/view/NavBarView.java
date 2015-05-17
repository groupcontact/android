package seaice.app.groupcontact.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import seaice.app.groupcontact.R;

/**
 * 仿iOS中Navigation Bar式样的View
 *
 * @author 周海兵
 */
public class NavBarView extends RelativeLayout {

    /* 标题View */
    TextView mTitleView;
    /* 标题 */
    String mTitle;

    /* BarItem的背景设置 */
    int mItemBackground;
    private static final int DEFAULT_ITEM_BACKGROUND = R.drawable.navbar_item_bg;
    float mItemTextSize;
    private static final float DEFAULT_ITEM_TEXT_SIZE = 18f;

    /* 左按钮 */
    View mLeftItem;
    int mLeftIcon;
    private static final int DEFAULT_LEFT_ICON = -1;
    String mLeftText;

    /* 右按钮 */
    View mRightItem;
    int mRightIcon;
    private static final int DEFAULT_RIGHT_ICON = -1;
    String mRightText;

    int mTitleColor = DEFAULT_TITLE_COLOR;
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#FFFFFFFF");

    float mTitleSize = DEFAULT_TITLE_SIZE;
    private static final float DEFAULT_TITLE_SIZE = 20f;

    float mItemMargin = DEFAULT_ITEM_MARGIN;
    private static final float DEFAULT_ITEM_MARGIN = 16;

    public NavBarView(Context context) {
        super(context);
        init(null, 0);
    }

    public NavBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NavBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NavBarView, defStyle, 0);

        mTitle = a.getString(R.styleable.NavBarView_navTitle);
        mRightIcon = a.getResourceId(R.styleable.NavBarView_navRightIcon, DEFAULT_RIGHT_ICON);
        mLeftIcon = a.getResourceId(R.styleable.NavBarView_navLeftIcon, DEFAULT_LEFT_ICON);
        mRightText = a.getString(R.styleable.NavBarView_navRightText);
        mLeftText = a.getString(R.styleable.NavBarView_navLeftText);
        mTitleColor = a.getColor(R.styleable.NavBarView_navTitleColor, DEFAULT_TITLE_COLOR);
        mTitleSize = a.getDimension(R.styleable.NavBarView_navTitleSize, DEFAULT_TITLE_SIZE);
        mItemMargin = a.getDimension(R.styleable.NavBarView_navItemMargin, DEFAULT_ITEM_MARGIN);
        mItemBackground = a.getResourceId(R.styleable.NavBarView_navItemBackground,
                DEFAULT_ITEM_BACKGROUND);
        mItemTextSize = a.getDimension(R.styleable.NavBarView_navItemTextSize, DEFAULT_ITEM_TEXT_SIZE);

        a.recycle();

        mTitleView = getTextView(mTitle, true);
        addView(mTitleView, getTitleLayoutParams());
        if (mRightIcon != DEFAULT_RIGHT_ICON) {
            setRightItem(mRightIcon);
        }
        if (mLeftIcon != DEFAULT_LEFT_ICON) {
            setLeftItem(mLeftIcon);
        }
        if (mRightText != null) {
            setRightItem(mRightText);
        }
        if (mLeftText != null) {
            setLeftItem(mLeftText);
        }
    }

    private TextView getTextView(String text, boolean title) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(mTitleColor);
        textView.setTextSize(title ? mTitleSize : mItemTextSize);
        textView.setText(text);

        return textView;
    }

    protected LayoutParams getTitleLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        return params;
    }

    /* 设置左边按钮, 文字形式的按钮 */
    public void setLeftItem(String text) {
        TextView textView = getTextView(text, false);
        setLeftItem(textView);
    }

    /* 设置左边按钮, 图片形式的按钮 */
    public void setLeftItem(int imgResId) {
        LinearLayout container = new LinearLayout(getContext());
        ImageView barItem = new ImageView(getContext());
        barItem.setImageResource(imgResId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        // 居中显示
        container.setGravity(Gravity.CENTER);
        // 左右Margin
        params.setMargins((int) mItemMargin, 0, (int) mItemMargin, 0);
        container.addView(barItem, params);
        container.setClickable(true);
        container.setBackgroundResource(mItemBackground);
        setLeftItem(container);
    }

    /* 设置前一个页面的标题, 在这里其实就是Android中的Home Button */
    public void setBackTitle(String backTitle) {
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setClickable(true);
        container.setBackgroundResource(mItemBackground);

        ImageView backIcon = new ImageView(getContext());
        backIcon.setImageResource(R.mipmap.ic_ab_back_holo_dark_am);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        // 左右Margin
        params.setMargins((int) mItemMargin, 0, 0, 0);
        container.addView(backIcon, params);

        TextView textView = getTextView(backTitle, false);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        // 左右Margin
        textParams.setMargins(0, 0, (int) mItemMargin, 0);
        container.addView(textView, textParams);

        container.setGravity(Gravity.CENTER);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        setLeftItem(container);
    }

    /* 直接使用View作为左部按钮 */
    public void setLeftItem(View view) {
        removeLeftItem();
        addView(view, getLeftItemLayoutParams());
        mLeftItem = view;
    }

    /* 移除左边按钮 */
    public void removeLeftItem() {
        if (mLeftItem != null) {
            removeView(mLeftItem);
        }
    }

    /* 设置左边按钮的监听器 */
    public void setLeftItemOnClickListener(OnClickListener listener) {
        if (mLeftItem != null) {
            mLeftItem.setOnClickListener(listener);
        }
    }

    /* 设置右边按钮, 文字形式的按钮 */
    public void setRightItem(String text) {
        TextView textView = getTextView(text, false);
        setRightItem(textView);
    }

    /* 设置右边按钮, 图片形式的按钮 */
    public void setRightItem(int imgResId) {
        LinearLayout container = new LinearLayout(getContext());
        ImageView barItem = new ImageView(getContext());
        barItem.setImageResource(imgResId);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        // 居中显示
        container.setGravity(Gravity.CENTER);
        // 左右Margin
        params.setMargins((int) mItemMargin, 0, (int) mItemMargin, 0);
        container.addView(barItem, params);
        container.setClickable(true);
        container.setBackgroundResource(mItemBackground);
        setRightItem(container);
    }

    /* 设置右边按钮, 任意View */
    public void setRightItem(View view) {
        removeRightItem();
        addView(view, getRightItemLayoutParams());
        mRightItem = view;
    }

    /* 移除右边按钮 */
    public void removeRightItem() {
        if (mRightItem != null) {
            removeView(mRightItem);
        }
    }

    public void setRightItemOnClickListener(OnClickListener listener) {
        if (mRightItem != null) {
            mRightItem.setOnClickListener(listener);
        }
    }

    protected LayoutParams getLeftItemLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_LEFT);
        params.addRule(CENTER_VERTICAL);
        return params;
    }

    protected LayoutParams getRightItemLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.addRule(CENTER_VERTICAL);
        return params;
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
