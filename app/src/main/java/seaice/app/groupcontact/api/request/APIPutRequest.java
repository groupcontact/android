package seaice.app.groupcontact.api.request;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class APIPutRequest extends APIPostRequest {

    public APIPutRequest(String url, Map<String, String> params, final Response.Listener<JSONArray> listener, final Response.ErrorListener errorListener) {
        super(url, params, listener, errorListener);
    }

    public APIPutRequest(String url, Map<String, String> params, final Response.ErrorListener errorListener, final Response.Listener<JSONObject> listener) {
        super(url, params, errorListener, listener);
    }


    @Override
    public int getMethod() {
        return Method.PUT;
    }
}
