package seaice.app.groupcontact.api.impl;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhb on 3/6/15.
 */
public abstract class AbstractAPImpl {

    public static final String PROTOCOL_CONTENT_TYPE = "application/x-www-form-urlencoded";

    protected Context mContext;

    protected RequestQueue mQueue;

    public AbstractAPImpl(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }
}
