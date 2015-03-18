package seaice.app.groupcontact.api;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhb on 2015/3/18.
 */
public class BaseCallback<T> implements Callback<T> {

    protected Context mContext;

    public BaseCallback(Context context) {
        mContext = context;
    }

    @Override
    public void call(T result) {

    }

    @Override
    public void error(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
