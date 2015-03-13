package seaice.app.groupcontact.api.impl;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import seaice.app.groupcontact.api.Callback;

/**
 * Created by zhb on 3/6/15.
 */
public abstract class AbstractAPImpl {

    protected Context mContext;

    protected RequestQueue mQueue;

    public AbstractAPImpl(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }

    public static class APIErrorListener implements Response.ErrorListener {

        private Callback<?> mCallback;

        public APIErrorListener(Callback<?> callback) {
            mCallback = callback;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mCallback.error(error.getMessage());
        }
    }
}
