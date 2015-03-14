package seaice.app.groupcontact.api.impl;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.api.request.APIGetRequest;
import seaice.app.groupcontact.api.request.APIPostRequest;

public class GroupAPImpl extends AbstractAPImpl implements GroupAPI {

    public GroupAPImpl(Context context) {
        super(context);
    }

    @Override
    public void list(Long gid, String accessToken, final Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "listUser?gid=" + gid + "&accessToken=" + accessToken;

        Request<String> request = new APIGetRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<UserAO> userList = new ArrayList<>();
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject obj = response.getJSONObject(i);
                        userList.add(UserAO.parse(obj));
                    }
                    cb.call(userList);
                } catch (JSONException e) {
                    cb.error(e.getMessage());
                }
            }
        }, new AbstractAPImpl.APIErrorListener(cb));

        mQueue.add(request);
    }

    public void search(String name, final Callback<List<GroupAO>> cb) {
        String url = Constants.baseUrl + "searchGroup?name=" + Uri.encode(name);

        Request<String> request = new APIGetRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<GroupAO> groupList = new ArrayList<>();
                    for (int i = 0; i < response.length(); ++i) {
                        JSONObject obj = response.getJSONObject(i);
                        groupList.add(GroupAO.parse(obj));
                    }
                    cb.call(groupList);
                } catch (JSONException e) {
                    cb.error(e.getMessage());
                }
            }
        }, new AbstractAPImpl.APIErrorListener(cb));

        mQueue.add(request);
    }

    @Override
    public void join(Long uid, Long gid, String accessToken, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());
        data.put("accessToken", accessToken);

        String url = Constants.baseUrl + "joinGroup";
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }

    @Override
    public void leave(Long uid, Long gid, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());

        String url = Constants.baseUrl + "leaveGroup";
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }
}
