package seaice.app.groupcontact.api.impl;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.Let;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.appbase.utils.CipherUtils;

public class GroupAPImpl extends VolleyBaseAPImpl implements GroupAPI {

    private static final String URL = Let.API_GROUP;

    public GroupAPImpl(Context context) {
        super(context);
    }

    @Override
    public void create(Long uid, String password, GroupAO group, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", group.getName());
        data.put("desc", group.getDesc());
        data.put("accessToken", CipherUtils.encrypt(group.getAccessToken(), Let.DEFAULT_KEY));
        data.put("modifyToken", CipherUtils.encrypt(group.getModifyToken(), Let.DEFAULT_KEY));
        data.put("uid", uid.toString());
        data.put("password", CipherUtils.encrypt(password, Let.DEFAULT_KEY));

        post(URL, data, cb, GeneralAO.class);
    }

    @Override
    public void list(Long gid, Callback<List<UserAO>> cb) {
        String url = URL + "/" + gid + "/members";
        getArray(url, cb, UserAO.class, Let.DEFAULT_KEY);
    }

    public void search(String name, Callback<List<GroupAO>> cb) {
        String url = URL + "?name=" + Uri.encode(name);
        getArray(url, cb, GroupAO.class, Let.DEFAULT_KEY);
    }
}
