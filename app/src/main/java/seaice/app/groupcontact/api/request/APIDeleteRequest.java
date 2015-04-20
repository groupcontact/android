package seaice.app.groupcontact.api.request;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zhb on 4/20/15.
 */
public class APIDeleteRequest extends APIPostRequest {

    public APIDeleteRequest(String url, Map<String, String> params, Response.Listener<JSONArray> listener,
                            Response.ErrorListener errorListener) {
        super(url, params, listener, errorListener);
    }

    public APIDeleteRequest(String url, Map<String, String> params, Response.ErrorListener errorListener,
                            Response.Listener<JSONObject> listener) {
        super(url, params, errorListener, listener);
    }

    public int getMethod() {
        return Method.DELETE;
    }
}
