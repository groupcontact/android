package seaice.app.groupcontact.api.impl;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.ConfigAPI;
import seaice.app.groupcontact.api.ao.ConfigAO;

/**
 * Created by zhb on 3/5/15.
 */
public class ConfigAPImpl implements ConfigAPI {

    public static final String url = "http://groupcontact.duapp.com/android.json";

    @Override
    public void load(Context context, ConfigAO customized, final Callback<ConfigAO> cb) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Request<JSONObject> request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ConfigAO configAO = new ConfigAO();
                // transform the response to be an instance of ConfigAO
                try {
                    configAO.setBaseUrl(response.getString("baseUrl"));
                    cb.call(configAO);
                } catch (JSONException e) {
                    cb.error("Data Error.");
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cb.error(error.getMessage());
            }

        });
        queue.add(request);
    }
}
