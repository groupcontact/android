package seaice.app.groupcontact.api.impl;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.RuntimeVar;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

public class GroupAPImpl extends VolleyBaseAPImpl implements GroupAPI {

    private static final String URL = "http://groupcontact.duapp.com/api/v2/groups";

    public GroupAPImpl(Context context) {
        super(context);
    }

    @Override
    public void create(String name, String desc, String access, String modify, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("desc", desc);
        data.put("accessToken", access);
        data.put("modifyToken", modify);

        String url = RuntimeVar.baseUrl + "createGroup";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void list(Long gid, Callback<List<UserAO>> cb) {
        String url = URL + "/" + gid + "/members";
        getArray(url, cb, UserAO.class, RuntimeVar.DEFAULT_KEY);
    }

    public void search(String name, Callback<List<GroupAO>> cb) {
        String url = URL + "?name=" + Uri.encode(name);
        getArray(url, cb, GroupAO.class, RuntimeVar.DEFAULT_KEY);
    }

    @Override
    public void join(Long uid, Long gid, String accessToken, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());
        data.put("accessToken", accessToken);

        String url = RuntimeVar.baseUrl + "joinGroup";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void leave(Long uid, Long gid, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());

        String url = RuntimeVar.baseUrl + "leaveGroup";
        post(url, data, cb, GeneralAO.class);
    }
}
