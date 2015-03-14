package seaice.app.groupcontact.api.impl;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

public class GroupAPImpl extends AbstractAPImpl implements GroupAPI {

    public GroupAPImpl(Context context) {
        super(context);
    }

    @Override
    public void list(Long gid, String accessToken, Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "listUser?gid=" + gid + "&accessToken=" + accessToken;
        getArray(url, cb, UserAO.class);
    }

    public void search(String name, Callback<List<GroupAO>> cb) {
        String url = Constants.baseUrl + "searchGroup?name=" + Uri.encode(name);
        getArray(url, cb, GroupAO.class);
    }

    @Override
    public void join(Long uid, Long gid, String accessToken, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());
        data.put("accessToken", accessToken);

        String url = Constants.baseUrl + "joinGroup";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void leave(Long uid, Long gid, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("gid", gid.toString());

        String url = Constants.baseUrl + "leaveGroup";
        post(url, data, cb, GeneralAO.class);
    }
}
