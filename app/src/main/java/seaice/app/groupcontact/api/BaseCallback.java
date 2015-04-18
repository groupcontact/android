package seaice.app.groupcontact.api;

import android.content.Context;
import android.widget.Toast;

/**
 * The basic adapter for <code>Callback</code> interface, the info method has default implementation
 * which just toast the message_normal out.
 *
 * @author zhb
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
    public void info(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
