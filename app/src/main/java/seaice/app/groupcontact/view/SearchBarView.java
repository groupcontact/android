package seaice.app.groupcontact.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.utils.AppUtils;

/**
 * 仿iOS式样的SearchBar
 *
 * @author 周海兵
 */
public class SearchBarView extends LinearLayout {

    /* 内部的Wrapper */
    LinearLayout mWrapperView;
    int mWrapperBackground;

    /* 搜索框内的hint */
    TextView mHintView;
    String mHintText;

    /* 搜索区域内的图标 */
    ImageView mIconView;
    int mIconResId;

    /* 输入框距上下左右边距 */
    float mEditMargin;
    private static final float DEFAULT_EDIT_MARGIN = 8;
    /* 输入框中图标和输入文本之间的边距 */
    float mEditInnerMargin;
    private static final float DEFAULT_EDIT_INNER_MARGIN = 2;
    /* 提示文本的颜色 */
    int mHintColor;
    private static final int DEFAULT_HINT_COLOR = Color.parseColor("#FFBCBCBC");
    /* 搜索文本的颜色 */
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FF565656");
    int mTextColor;
    /* 文本的大小 */
    float mTextSize;
    private static final float DEFAULT_TEXT_SIZE = 8;

    /* 输入框 */
    EditText mEditText;

    /* 输入法控制 */
    InputMethodManager mIMManager;

    private TextWatcher mListener;

    public SearchBarView(Context context) {
        super(context);
        init(null);
    }

    public SearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SearchBarView);

        mWrapperBackground = a.getResourceId(R.styleable.SearchBarView_searchWrapperBackground,
                R.drawable.searchbar_wrapper_bg);
        mIconResId = a.getResourceId(R.styleable.SearchBarView_searchLeftIcon, R.mipmap.ic_action_search);
        mEditMargin = a.getDimension(R.styleable.SearchBarView_searchEditMargin,
                AppUtils.getPix(getContext(), DEFAULT_EDIT_MARGIN));
        mEditInnerMargin = a.getDimension(R.styleable.SearchBarView_searchEditInnerMargin,
                AppUtils.getPix(getContext(), DEFAULT_EDIT_INNER_MARGIN));
        mHintColor = a.getColor(R.styleable.SearchBarView_searchHintColor, DEFAULT_HINT_COLOR);
        mTextColor = a.getColor(R.styleable.SearchBarView_searchTextColor, DEFAULT_TEXT_COLOR);
        mTextSize = a.getDimension(R.styleable.SearchBarView_searchTextSize,
                AppUtils.getPix(getContext(), DEFAULT_TEXT_SIZE));
        mHintText = a.getString(R.styleable.SearchBarView_searchHintText);

        a.recycle();

        /* 横向排列 */
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.parseColor("#FFCDCDCD"));

        mWrapperView = new LinearLayout(getContext());
        mWrapperView.setGravity(Gravity.CENTER);
        if (mWrapperBackground != -1) {
            mWrapperView.setBackgroundResource(mWrapperBackground);
        }

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins((int) mEditMargin, (int) mEditMargin, (int) mEditMargin, (int) mEditMargin);
        addView(mWrapperView, params);

        mIconView = new ImageView(getContext());
        if (mIconResId != -1) {
            mIconView.setImageResource(mIconResId);
        }
        LayoutParams iconParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iconParams.setMargins((int) mEditInnerMargin * 2, 0, (int) mEditInnerMargin, 0);
        mWrapperView.addView(mIconView, iconParams);


        mWrapperView.setClickable(true);
        /* 只有当设置了OnSearchListener点击WrapperView才能起作用 */
        mWrapperView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enterEditMode();
            }
        });

        enterViewMode();

        mIMManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void setTextWatcher(TextWatcher listener) {
        mListener = listener;
    }

    public void enterEditMode() {
        // 移除TextHint
        if (mHintView != null) {
            mWrapperView.removeView(mHintView);
            mHintView = null;
        }

        // 将Icon移到最左边
        TranslateAnimation animation = new TranslateAnimation(0.5f, 0.0f, 0.5f, 0.5f);
        animation.setDuration(500);
        mIconView.startAnimation(animation);

        mEditText = new EditText(getContext());
        mEditText.setBackgroundColor(Color.WHITE);
        mEditText.setPadding(0, 0, 0, 0);
        mEditText.setHint(mHintText);
        mEditText.setTextSize(mTextSize);
        mEditText.setHintTextColor(mHintColor);
        LayoutParams editParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWrapperView.addView(mEditText, editParams);

        if (mListener != null) {
            mEditText.addTextChangedListener(mListener);
        }

        mEditText.requestFocus();
        mIMManager.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void enterViewMode() {
        if (mEditText != null) {
            mIMManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            mWrapperView.removeView(mEditText);
            mEditText = null;

            // 将Icon移到中间
            TranslateAnimation animation = new TranslateAnimation(.0f, .5f, .5f, .5f);
            animation.setDuration(2000);
            mIconView.startAnimation(animation);
        }

        mHintView = new TextView(getContext());
        mHintView.setText(mHintText);
        mHintView.setTextSize(mTextSize);
        mHintView.setTextColor(mHintColor);
        LayoutParams hintParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        hintParams.setMargins((int) mEditInnerMargin, 0, (int) mEditInnerMargin, 0);
        mWrapperView.addView(mHintView, hintParams);
    }

    /* 委托至mWrapperView */
    @Override
    public void setOnClickListener(OnClickListener listener) {
        mWrapperView.setOnClickListener(listener);
    }
}
