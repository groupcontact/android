package seaice.app.groupcontact;

import android.os.Bundle;

import seaice.app.appbase.BaseActivity;

/**
 * 意见反馈页面
 *
 * @author zhb
 */
public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected boolean needDagger() {
        return false;
    }

}
