package seaice.app.groupcontact;

import android.os.Bundle;
import android.widget.EditText;

import java.util.Collections;

import butterknife.InjectView;
import butterknife.OnClick;
import seaice.app.appbase.BaseActivity;
import seaice.app.appbase.view.TableView;
import seaice.app.groupcontact.adapter.MessageListAdapter;
import seaice.app.groupcontact.api.ao.Message;

/**
 * 意见反馈页面
 *
 * @author zhb
 */
public class FeedbackActivity extends BaseActivity {

    @InjectView(R.id.feedbackList)
    TableView mFeedbackListContainer;

    @InjectView(R.id.edittext_content)
    EditText mEditText;

    MessageListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new MessageListAdapter(this, Collections.<Message>emptyList());
        mFeedbackListContainer.setAdapter(mAdapter);

    }

    @OnClick(R.id.sendFeedback)
    public void sendFeecback() {
        Message message = new Message();
        message.setMessage(mEditText.getText().toString());

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
