package seaice.app.groupcontact.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.utils.AppUtils;

/**
 * 辅助实现PopOverView的View
 */
public class AnchorView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private Paint mPaint;

    public AnchorView(Context context) {
        super(context);
        init(null, 0);
    }

    public AnchorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AnchorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.AnchorView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.AnchorView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.AnchorView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.AnchorView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.AnchorView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.AnchorView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();


        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int contentWidth = getWidth();
        int contentHeight = getHeight();

        float[] points = new float[]{
                contentWidth - AppUtils.getPix(getContext(), 16), contentHeight,
                contentWidth - AppUtils.getPix(getContext(), 8), 0,
                contentWidth - AppUtils.getPix(getContext(), 0), contentHeight
                // contentWidth - AppUtils.getPix(getContext(), 16), contentHeight
        };

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#000000"));

        canvas.drawLines(points, mPaint);
    }

}
