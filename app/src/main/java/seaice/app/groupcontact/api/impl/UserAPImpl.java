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

    private static final String URL = "http://groupcontact.duapp.com/api/v2/users";

    public UserAPImpl(Context context) {
        super(context);
    }

    @Override
    public void register(String phone, String password, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("phone", phone);
        data.put("password", password);

        post(URL, data, cb, GeneralAO.class);
    }

    @Override
    public void save(UserAO user, String password, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("ext", user.getExt());
        data.put("password", password);

        put(URL + "/" + user.getUid(), data, cb, GeneralAO.class);
    }

    @Override
    public void edit(UserAO user, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("uid", user.getUid().toString());
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("ext", user.getExt());

        String url = Constants.baseUrl + "editUser";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void listGroup(Long uid, String key, Callback<List<GroupAO>> cb) {
        String url = URL + "/" + uid + "/groups?key=" + key;
        getArray(url, cb, GroupAO.class, key);
    }

    @Override
    public void listFriend(Long uid, String key, Callback<List<UserAO>> cb) {
        String url = URL + "/" + uid + "/friends?key=" + key;
        getArray(url, cb, UserAO.class, key);
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
    public void find(Long uid, String key, Callback<List<UserAO>> cb) {
        String url = URL + "/" + uid + "?key=" + key;
        getArray(url, cb, UserAO.class, key);
    }
}
