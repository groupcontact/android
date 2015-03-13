package seaice.app.groupcontact.api.request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zhb on 2015/3/11.
 */
public class APIGetRequest extends StringRequest {

    /**
     * Use this constructor If you want to receive a JSONArray
     *
     * @param url
     * @param listener
     * @param errorListener
     */
    public APIGetRequest(String url, final Response.Listener<JSONArray> listener, final Response.ErrorListener errorListener) {
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
    }

    /**
     * Use this constructor If you you want to receive a JSONObject
     *
     * @param url
     * @param errorListener
     * @param listener
     */
    public APIGetRequest(String url, final Response.ErrorListener errorListener, final Response.Listener<JSONObject> listener) {
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
    }

    @Override
    public int getMethod() {
        return Method.GET;
    }
}
