package seaice.app.groupcontact.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
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

    /* 设置左边按钮 */
    protected void addLeftButton() {

    }

    /* 设置右边按钮 */
    protected void addRightButton() {

    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
