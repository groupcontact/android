package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.Var;
import seaice.app.groupcontact.utils.BitmapUtils;
import seaice.app.groupcontact.view.TableAdapter;

public class ProfileAdapter extends TableAdapter {

    String[] mTextList;

    Integer[] mDrawableList;

    public ProfileAdapter(Context context, String[] textList) {
        super(context);

        mTextList = textList;
        mDrawableList = new Integer[]{
                R.drawable.qrcode,
                R.drawable.scan,
                R.drawable.feedback
        };

        notifyDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return 3;
    }

    @Override
    public int getRowCount(int section) {
        return section == 0 ? 1 : section == 1 ? 3 : 2;
    }

    @Override
    public View getRow(int section, int row) {
        if (section == 0) {
            return getMainView();
        }
        if (section == 1) {
            return getMenuView(row);
        }
        if (section == 2) {
            return getSettingView(row);
        }
        return null;
    }

    private View getMainView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_avatar, null);
        ImageView avatarView = (ImageView) rootView.findViewById(R.id.profile_avatar);
        avatarView.setImageBitmap(BitmapUtils.getCroppedBitmap(BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.avatar)));
        TextView nameView = (TextView) rootView.findViewById(R.id.profile_name);
        nameView.setText(Var.userAO.getName());
        TextView phoneView = (TextView) rootView.findViewById(R.id.profile_phone);
        phoneView.setText(Var.userAO.getPhone());
        return rootView;
    }

    private View getMenuView(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_profile_menu, null);
        TextView textView = (TextView) rootView.findViewById(R.id.profile_menu_text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_menu_icon);
        textView.setText(mTextList[row]);
        imageView.setImageResource(mDrawableList[row]);
        return rootView;
    }

    private View getSettingView(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_profile_menu, null);
        TextView textView = (TextView) rootView.findViewById(R.id.profile_menu_text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_menu_icon);
        textView.setText(row == 0 ? "修改密码" : "退出登录");
        imageView.setImageResource(row == 0 ? R.drawable.qrcode : R.drawable.feedback);
        return rootView;
    }
}
