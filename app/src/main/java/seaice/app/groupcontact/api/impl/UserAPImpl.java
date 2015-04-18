package seaice.app.groupcontact.api.impl;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.Constants;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * API about user manipulation.
 */
public class UserAPImpl extends VolleyBaseAPImpl implements UserAPI {

    public UserAPImpl(Context context) {
        super(context);
    }

    @Override
    public void create(UserAO user, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("phone_normal", user.getPhone());

        String url = Constants.baseUrl + "createUser";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void edit(UserAO user, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", user.getUid().toString());
        data.put("name", user.getName());
        data.put("phone_normal", user.getPhone());
        data.put("ext", user.getExt());

        String url = Constants.baseUrl + "editUser";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void listGroup(Long uid, Callback<List<GroupAO>> cb) {
        String url = Constants.baseUrl + "listGroup?uid=" + uid;
        getArray(url, cb, GroupAO.class);
    }

    @Override
    public void listFriend(Long uid, String name, Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "listFriend?uid=" + uid + "&name=" + Uri.encode(name);
        getArray(url, cb, UserAO.class);
    }

    @Override
    public void addFriend(Long uid, String fname, String fphone, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("fname", fname);
        data.put("fphone", fphone);

        String url = Constants.baseUrl + "addFriend";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void deleteFriend(Long uid, String name, Long fid, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", uid.toString());
        data.put("name", name);
        data.put("fid", fid.toString());

        String url = Constants.baseUrl + "deleteFriend";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void find(Long uid, String name, Callback<List<UserAO>> cb) {
        String url = Constants.baseUrl + "findUser?uid=" + uid + "&name=" + Uri.encode(name);
        getArray(url, cb, UserAO.class);
    }
}
