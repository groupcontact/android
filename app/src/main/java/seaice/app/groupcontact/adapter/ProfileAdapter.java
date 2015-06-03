package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.Var;
import seaice.app.appbase.view.TableAdapter;

public class ProfileAdapter extends TableAdapter {

    String[] mTextList;

    Integer[] mDrawableList;

    String[] mSettings;

    public ProfileAdapter(Context context, String[] textList) {
        super(context);

        mTextList = textList;
        mDrawableList = new Integer[]{
                R.drawable.qrcode,
                R.drawable.scan,
                R.drawable.feedback
        };
        mSettings = context.getResources().getStringArray(R.array.profile_settings);

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
        String name = Var.userAO.getName();
        avatarView.setImageDrawable(TextDrawable.builder()
                .buildRoundRect(name.substring(name.length() - 1),
                        ColorGenerator.MATERIAL.getColor(name), 0));
        TextView nameView = (TextView) rootView.findViewById(R.id.profile_name);
        nameView.setText(Var.userAO.getName());
        TextView phoneView = (TextView) rootView.findViewById(R.id.profile_phone);
        phoneView.setText(Var.userAO.getPhone());
        return rootView;
    }

    private View getMenuView(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_disclosure, null);
        TextView textView = (TextView) rootView.findViewById(R.id.menuText);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.menuIcon);
        textView.setText(mTextList[row]);
        imageView.setImageResource(mDrawableList[row]);
        return rootView;
    }

    private View getSettingView(int row) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_disclosure, null);
        TextView textView = (TextView) rootView.findViewById(R.id.menuText);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.menuIcon);
        textView.setText(mSettings[row]);
        imageView.setImageResource(row == 0 ? R.drawable.password : R.drawable.exit);
        return rootView;
    }
}
