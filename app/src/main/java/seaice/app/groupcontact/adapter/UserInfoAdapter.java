package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.view.TableAdapter;

public class UserInfoAdapter extends TableAdapter {

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

    public View getHeader() {
        LinearLayout container = new LinearLayout(mContext);
        container.setGravity(Gravity.CENTER);
        ImageView avatarView = new ImageView(mContext);
        avatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(mName.substring(mName.length() - 1),
                        ColorGenerator.MATERIAL.getColor(mName), 0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160,
                160);
        params.setMargins(0, 32, 0, 32);
        container.addView(avatarView, params);
        return container;
    }

    @Override
    public int getRowCount(int section) {
        return section == 0 ? 3 : 1;
    }

    @Override
    public View getRow(int section, int row) {
        if (section == 0) {
            return getMainField(row);
        } else {
            return getExtField(row);
        }
    }

    @Override
    public String getSectionHeader(int section) {
        if (section == 0) {
            return null;
        }
        return "";
    }

    @Override
    public int getSectionCount() {
        return 2;
    }

    public View getMainField(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_profile_menu, null);
        TextView textView = (TextView) rootView.findViewById(R.id.profile_menu_text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_menu_icon);
        String text = row == 0 ? mPhone : row == 1 ? mEmail : mWechat;
        textView.setText(text);
        if (text == null) {
            textView.setText(mContext.getString(R.string.empty_now));
            textView.setTextColor(mContext.getResources().getColor(R.color.tipColor));
        }
        imageView.setImageResource(row == 0 ? R.drawable.scan : row == 1 ? R.drawable.exit :
                R.drawable.feedback);
        return rootView;
    }

    public View getExtField(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_profile_menu, null);
        TextView textView = (TextView) rootView.findViewById(R.id.profile_menu_text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_menu_icon);
        textView.setText("877498144");
        imageView.setImageResource(R.drawable.scan);
        return rootView;
    }
}
