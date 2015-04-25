package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import seaice.app.groupcontact.R;
import seaice.app.groupcontact.Var;
import seaice.app.groupcontact.utils.BitmapUtils;

/**
 * Created by zhb on 4/25/15.
 */
public class ProfileAdapter extends BaseAdapter {

    String[] mTextList;

    Integer[] mDrawableList;

    Context mContext;

    public ProfileAdapter(Context context, String[] textList) {
        mContext = context;
        mTextList = textList;
    }

    @Override
    public int getCount() {
        return mTextList.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        if (position == 0) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.item_avatar, null);
            ImageView avatarView = (ImageView) rootView.findViewById(R.id.profile_avatar);
            avatarView.setImageBitmap(BitmapUtils.getCroppedBitmap(BitmapFactory.decodeResource(
                    mContext.getResources(), R.drawable.avatar)));
            TextView nameView = (TextView) rootView.findViewById(R.id.profile_name);
            nameView.setText(Var.userAO.getName());
        } else {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.item_profile_menu, null);
            TextView textView = (TextView) rootView.findViewById(R.id.profile_menu_text);
//            ImageView imageView = (ImageView) rootView.findViewById(R.id.profile_menu_icon);
            textView.setText(mTextList[position - 1]);
//            imageView.setImageResource(mDrawableList[position - 1]);
        }
        return rootView;
    }
}
