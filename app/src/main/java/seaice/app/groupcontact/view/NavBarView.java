package seaice.app.groupcontact.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 仿iOS中Navigation Bar式样的View
 *
 * @author 周海兵
 */
public class NavBarView extends RelativeLayout {

    /* 标题 */
    TextView mTitleView;

    int mTitleColor = DEFAULT_TITLE_COLOR;
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#FFFFFFFF");

    int mTitleSize = DEFAULT_TITLE_SIZE;
    private static final int DEFAULT_TITLE_SIZE = 22;

    int mBarItemMargin = DEFAULT_BAR_ITEM_MARGIN;
    private static final int DEFAULT_BAR_ITEM_MARGIN = 16;

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
//
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.NavBarView, defStyle, 0);
//
//
//        a.recycle();

        addTitleView();
    }

    /* 添加标题View */
    protected void addTitleView() {
        mTitleView = new TextView(getContext());
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setTextSize(mTitleSize);
        addView(mTitleView, getTitleLayoutParams());
    }

    protected LayoutParams getTitleLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        return params;
    }

    /* 设置左边按钮, 文字形式的按钮 */
    public void addLeftBarItem(String text) {

    }

    /* 设置左边按钮, 图片形式的按钮 */
    public void addLeftBarItem(int imgResId) {

    }

    /* 设置前一个页面的标题, 在这里其实就是Android中的Home Button */
    public void setBackTitle(String backTitle) {

    }

    /* 设置右边按钮, 文字形式的按钮 */
    public void addRightBarItem(String text) {

    }

    /* 设置右边按钮, 图片形式的按钮 */
    public void addRightBarItem(int imgResId, OnClickListener listener) {
        ImageView barItem = new ImageView(getContext());
        barItem.setImageResource(imgResId);
        barItem.setOnClickListener(listener);
        addView(barItem, getRightBarItemLayoutParams());
    }

    protected LayoutParams getRightBarItemLayoutParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.setMargins(0, 0, mBarItemMargin, 0);
        params.addRule(CENTER_VERTICAL);
        return params;
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
