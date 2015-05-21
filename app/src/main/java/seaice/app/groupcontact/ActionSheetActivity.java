package seaice.app.groupcontact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import seaice.app.groupcontact.adapter.ActionSheetAdapter;
import seaice.app.groupcontact.view.TableView;


public class ActionSheetActivity extends BaseActivity implements TableView.OnCellClickListener {

    public static final String TITLE = "ACTION_SHEET_TITLE";

    public static final String MESSAGE = "ACTION_SHEET_MESSAGE";

    public static final String ACTIONS = "ACTION_SHEET_ACTIONS";

    public static final String ICONS = "ACTION_SHEET_ICONS";

    public static final String CANCEL = "ACTION_SHEET_CANCEL";

    @InjectView(R.id.actionSheet)
    TableView mActionSheetView;

    String[] mActions;

    int[] mIcons;

    String mTitle;

    String mMessage;

    boolean mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet);

        ButterKnife.inject(this);

        mActions = getIntent().getStringArrayExtra(ACTIONS);
        mIcons = getIntent().getIntArrayExtra(ICONS);
        mTitle = getIntent().getStringExtra(TITLE);
        mMessage = getIntent().getStringExtra(MESSAGE);
        mCancel = getIntent().getBooleanExtra(CANCEL, true);

        mActionSheetView.setAdapter(new ActionSheetAdapter(
                this, mTitle, mMessage, mActions, mIcons, mCancel));

//        TranslateAnimation animation = new TranslateAnimation(.0f, .0f, 1f, 0f);
//        animation.setDuration(500);
//        mActionSheetView.startAnimation(animation);

        mActionSheetView.setOnCellClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onCellClick(AdapterView<?> parent, View view, int section, int row, long id) {
        if (section == 1) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        if (row % 2 == 1) {
            return;
        }
        Intent data = new Intent();
        data.putExtra("ACTION", row / 2);
        setResult(RESULT_OK, data);
        finish();
    }
}
