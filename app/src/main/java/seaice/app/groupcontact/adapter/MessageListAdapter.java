package seaice.app.groupcontact.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import seaice.app.appbase.adapter.BaseTableAdapter;
import seaice.app.groupcontact.api.ao.Message;

public class MessageListAdapter extends BaseTableAdapter {

    List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        super(context);

        mMessageList = messageList;
    }

    @Override
    public int getRowCount(int section) {
        return mMessageList.size();
    }

    @Override
    public View getRow(int section, int row, View convertView, ViewGroup parent) {
        return null;
    }


    public void appendMessage(Message message) {
        mMessageList.add(message);

        notifyDataSetChanged();
    }
}
