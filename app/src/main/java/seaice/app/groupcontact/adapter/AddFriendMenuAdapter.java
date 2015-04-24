package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import seaice.app.groupcontact.R;

/**
 * Created by zhb on 4/23/15.
 */
public class AddFriendMenuAdapter extends BaseAdapter {

    private Context mContext;

    private String[] mTexts;

    public AddFriendMenuAdapter(Context context) {
        mContext = context;

        mTexts = mContext.getResources().getStringArray(R.array.add_friend_menu);
    }

    @Override
    public int getCount() {
        return 2;
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
        String text = mTexts[position];
        View rootView;

        if (position == 0) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.menu_add_friend, null);
        } else {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.menu_scan_qrcode, null);
        }
        TextView textView = (TextView) rootView.findViewById(R.id.menuText);
        textView.setText(text);
        return rootView;
    }
}
