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
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.api.request.APIGetRequest;
import seaice.app.groupcontact.api.request.APIPostRequest;

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
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }

    @Override
    public void edit(UserAO user, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", user.getUid().toString());
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("ext", user.getExt());

        String url = Constants.baseUrl + "editUser";
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }

    @Override
    public void listGroup(Long uid, final Callback<List<GroupAO>> cb) {
        String url = Constants.baseUrl + "listGroup?uid=" + uid;

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
        }, new APIErrorListener(cb));

        mQueue.add(request);
    }

    @Override
    public void listFriend(Long uid, String name, final Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "listFriend?uid=" + uid + "&name=" + Uri.encode(name);

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
        }, new APIErrorListener(cb));

        mQueue.add(request);
    }

    @Override
    public void addFriend(Long uid, String fname, String fphone, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("fname", fname);
        data.put("fphone", fphone);

        String url = Constants.baseUrl + "addFriend";
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }

    @Override
    public void deleteFriend(Long uid, String name, Long fid, final Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("name", name);
        data.put("fid", fid.toString());

        String url = Constants.baseUrl + "deleteFriend";
        Request<String> request = new APIPostRequest(url, data, new APIErrorListener(cb), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cb.call(GeneralAO.parse(response));
            }
        });

        mQueue.add(request);
    }

    @Override
    public void find(Long uid, String name, final Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "findUser?uid=" + uid + "&name=" + Uri.encode(name);

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
        }, new APIErrorListener(cb));

        mQueue.add(request);
    }
}
