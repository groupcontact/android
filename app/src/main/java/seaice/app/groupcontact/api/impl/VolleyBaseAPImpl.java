package seaice.app.groupcontact.api.impl;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.ao.ConfigAO;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.api.request.APIGetRequest;
import seaice.app.groupcontact.api.request.APIPostRequest;
import seaice.app.groupcontact.api.request.APIPutRequest;


public abstract class VolleyBaseAPImpl {

    protected Context mContext;

    protected RequestQueue mQueue;

    public VolleyBaseAPImpl(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }

    private static <T> T parseResponse(JSONObject response, Class<T> typedClass) {
        if (typedClass.equals(GeneralAO.class)) {
            return (T) GeneralAO.parse(response);
        } else if (typedClass.equals(UserAO.class)) {
            return (T) UserAO.parse(response);
        } else if (typedClass.equals(GroupAO.class)) {
            return (T) GroupAO.parse(response);
        } else if (typedClass.equals(ConfigAO.class)) {
            return (T) ConfigAO.parse(response);
        }
        return null;
    }

    protected <T> void get(String url, Callback<T> cb, Class<T> typedClass, String key) {
        Request<String> request = new APIGetRequest(url, new APIErrorListener(cb),
                new APIJSONObjectListener<>(cb, typedClass), key);
        mQueue.add(request);
    }

    protected <T> void getArray(String url, Callback<List<T>> cb, Class<T> typedClass, String key) {
        Request<String> request = new APIGetRequest(url, new APIJSONArrayListener<>(cb, typedClass),
                new APIErrorListener(cb), key);
        mQueue.add(request);
    }

    protected <T> void post(String url, Map<String, String> params, Callback<T> cb, Class<T> typedClass) {
        Request<String> request = new APIPostRequest(url, params, new APIErrorListener(cb),
                new APIJSONObjectListener<>(cb, typedClass));
        mQueue.add(request);
    }

    protected <T> void put(String url, Map<String, String> params, Callback<T> cb, Class<T> typedClass) {
        Request<String> request = new APIPutRequest(url, params, new APIErrorListener(cb),
                new APIJSONObjectListener<>(cb, typedClass));
        mQueue.add(request);
    }

    public static class APIErrorListener implements Response.ErrorListener {

        private Callback<?> mCallback;

        public APIErrorListener(Callback<?> callback) {
            mCallback = callback;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mCallback.info(error.getMessage());
        }
    }

    public static class APIJSONArrayListener<T> implements Response.Listener<JSONArray> {

        private Callback<List<T>> mCallback;

        private Class<T> mTypedClass;

        public APIJSONArrayListener(Callback<List<T>> callback, Class<T> typedClass) {
            mCallback = callback;
            mTypedClass = typedClass;
        }

        @Override
        public void onResponse(JSONArray response) {
            List<T> result = new ArrayList<>();
            for (int i = 0; i < response.length(); ++i) {
                try {
                    result.add(parseResponse(response.getJSONObject(i), mTypedClass));
                } catch (JSONException e) {
                    mCallback.info(e.getMessage());
                }
            }
            mCallback.call(result);
        }
    }

    public static class APIJSONObjectListener<T> implements Response.Listener<JSONObject> {

        private Callback<T> mCallback;

        private Class<T> mTypedClass;

        public APIJSONObjectListener(Callback<T> callback, Class<T> typedClass) {
            mCallback = callback;
            mTypedClass = typedClass;
        }

        @Override
        public void onResponse(JSONObject response) {
            mCallback.call(parseResponse(response, mTypedClass));
        }
    }
}
