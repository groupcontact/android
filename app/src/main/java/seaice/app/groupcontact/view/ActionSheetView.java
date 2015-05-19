package seaice.app.groupcontact.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import seaice.app.groupcontact.utils.AppUtils;

public class ActionSheetView extends TableView {

    ActionSheetAdapter mAdapter;

    public ActionSheetView(Context context) {
        super(context);
        init(context, null);
    }

    public ActionSheetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    public void setActionSheetAdapter(ActionSheetAdapter adapter) {
        mAdapter = adapter;

        setAdapter(new TableAdapter(getContext()) {

            public View getHeader() {
                 /* 标题和消息 */
                return getTitleAndMessageView(mAdapter.getTitle(), mAdapter.getMessage());
            }

            public View getFooter() {
                return getCancelView();
            }

            @Override
            public int getRowCount(int section) {
                /* Action列表 */
                return mAdapter.getActionNameList().size();
            }

            @Override
            public View getRow(int section, int row) {
                /* ActionSheet的每一行Action */
                return getActionView(mAdapter.getActionNameList().get(row),
                        mAdapter.getActionIconList().get(row));
            }

            @Override
            public int getSectionCount() {
                return 1;
            }

            @Override
            public String getSectionFooter(int section) {
                /* 和取消按钮之间要有一点空隙 */
                return "";
            }
        });
    }

    private View getTitleAndMessageView(String title, String message) {
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);
        if (title != null) {
            TextView titleView = new TextView(getContext());
            titleView.setText(title);
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            addView(titleView, titleParams);
        }
        if (message != null) {
            TextView messageView = new TextView(getContext());
            messageView.setText(message);
            LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            addView(messageView, messageParams);
        }
        return container;
    }

    /* 每一行的动作选项 */
    private View getActionView(String action, int iconResId) {
        LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);
        ImageView iconView = new ImageView(getContext());
        iconView.setImageResource(iconResId);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                (int) (AppUtils.getPix(getContext(), 24)),
                (int) (AppUtils.getPix(getContext(), 24)));
        container.addView(iconView, iconParams);
        TextView actionView = new TextView(getContext());
        actionView.setText(action);
        LinearLayout.LayoutParams actionParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        container.addView(actionView, actionParams);
        return container;
    }

    private View getCancelView() {
        LinearLayout container = new LinearLayout(getContext());
        container.setGravity(Gravity.CENTER);
        container.setOrientation(LinearLayout.VERTICAL);
        TextView cancelView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        container.addView(cancelView, params);
        return container;
    }

}
