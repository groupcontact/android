package seaice.app.groupcontact.api.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * A little customization. We use string request while we want to receive json response.
 *
 * @author zhb
 */
public class APIRequest extends StringRequest {

    private Map<String, String> mParams;

    /**
     * Use this constructor If you want to receive a JSONArray
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public APIRequest(String url, Map<String, String> params, final Response.Listener<JSONArray> listener, final Response.ErrorListener errorListener) {
        super(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.onResponse(new JSONArray(response));
                } catch (JSONException e) {
                    errorListener.onErrorResponse(new VolleyError(e.getMessage()));
                }
            }
        }, errorListener);

        mParams = params;
    }

    /**
     * Use this constructor If you you want to receive a JSONObject
     *
     * @param url
     * @param params
     * @param errorListener
     * @param listener
     */
    public APIRequest(String url, Map<String, String> params, final Response.ErrorListener errorListener, final Response.Listener<JSONObject> listener) {
        super(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listener.onResponse(new JSONObject(response));
                } catch (JSONException e) {
                    errorListener.onErrorResponse(new VolleyError(e.getMessage()));
                }
            }
        }, errorListener);

        mParams = params;
    }

    @Override
    public int getMethod() {
        return Method.POST;
    }

    protected Map<String, String> getParams() {
        return mParams;
    }
}
