package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import seaice.app.appbase.adapter.BaseTableAdapter;
import seaice.app.appbase.utils.AppUtils;
import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.UserAO;

public class UserInfoAdapter extends BaseTableAdapter {

    private String mName;

    private String mPhone;

    private String mEmail;

    private String mWechat;

    public UserInfoAdapter(Context context, UserAO userAO) {
        super(context);

        mName = userAO.getName();
        mPhone = userAO.getPhone();
        try {
            JSONObject extObj = new JSONObject(userAO.getExt());
            mEmail = extObj.optString("email", "");
            if (mEmail.trim().equals("")) {
                mEmail = null;
            }
            mWechat = extObj.optString("wechat", "");
            if (mWechat.trim().equals("")) {
                mWechat = null;
            }
        } catch (JSONException e) {
            // ignore this;
        }

        notifyDataSetChanged();
    }

    @Override
    public boolean hasHeader() {
        return true;
    }

    public View getHeader(ViewGroup parent) {
        LinearLayout container = new LinearLayout(mContext);
        container.setGravity(Gravity.CENTER);
        ImageView avatarView = new ImageView(mContext);
        avatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(mName.substring(mName.length() - 1),
                        ColorGenerator.MATERIAL.getColor(mName), 0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) AppUtils.getPix(mContext, 80),
                (int) AppUtils.getPix(mContext, 80));
        params.setMargins(0, (int) AppUtils.getPix(mContext, 16), 0, (int) AppUtils.getPix(mContext, 16));
        container.addView(avatarView, params);
        return container;
    }

    @Override
    public boolean hasFooter() {
        return false;
    }

    @Override
    public View getFooter(ViewGroup parent) {
        return null;
    }

    @Override
    public int getRowCount(int section) {
        return 3;
    }

    @Override
    public View getRow(int section, int row, View convertView, ViewGroup parent) {
        return getMainField(row, parent);
    }

    @Override
    public int getSectionCount() {
        return 1;
    }

    public View getMainField(int row, ViewGroup parent) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_disclosure,
                parent, false);
        TextView textView = (TextView) rootView.findViewById(R.id.menuText);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.menuIcon);
        String text = row == 0 ? mPhone : row == 1 ? mEmail : mWechat;
        textView.setText(text);
        if (text == null) {
            textView.setText(mContext.getString(R.string.empty_now));
            textView.setTextColor(mContext.getResources().getColor(R.color.tipColor));
            rootView.findViewById(R.id.menuDisclosure).setVisibility(View.INVISIBLE);
        }
        /* 除了手机号和邮箱,其他的都没有动作 */
        if (row >= 2) {
            rootView.findViewById(R.id.menuDisclosure).setVisibility(View.INVISIBLE);
        }
        imageView.setImageResource(row == 0 ? R.drawable.phone : row == 1 ? R.drawable.email :
                R.drawable.wechat);
        return rootView;
    }
}
