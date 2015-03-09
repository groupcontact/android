package seaice.app.groupcontact.api.impl;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * API about user manipulation.
 */
public class UserAPImpl extends AbstractAPImpl implements UserAPI {

    public UserAPImpl(Context context) {
        super(context);
    }

    @Override
    public void create(final UserAO user, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("phone", user.getPhone());

        String url = Constants.baseUrl + "createUser";
        Request<String> request = new APIRequest(url, data, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cb.error(error.getMessage());
            }
        }, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GeneralAO result = new GeneralAO();
                // transform the response to be an instance of ConfigAO
                try {
                    result.setStatus(response.getInt("status"));
                    result.setId(response.optLong("id", 0L));
                    result.setInfo(response.optString("info", ""));
                } catch (JSONException e) {
                    cb.error(e.getMessage());
                }
                cb.call(result);
            }
        });
        mQueue.add(request);
    }

    @Override
    public void edit(UserAO user, Callback<GeneralAO> cb) {

    }

    @Override
    public void join(Long uid, Long gid, String accessToken, Callback<GeneralAO> cb) {

    }

    @Override
    public void leave(Long uid, Long gid, Callback<GeneralAO> cb) {

    }
}
