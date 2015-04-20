package seaice.app.groupcontact.api.request;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zhb on 4/20/15.
 */
public class APIDeleteRequest extends APIGetRequest {


    public APIDeleteRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener,
                            String key) {
        super(url, listener, errorListener, key);
    }

    public APIDeleteRequest(String url, Response.ErrorListener errorListener, Response.Listener<JSONObject> listener,
                            String key) {
        super(url, errorListener, listener, key);
    }

    public int getMethod() {
        return Method.DELETE;
    }
}
