package seaice.app.groupcontact.api.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seaice.app.groupcontact.Let;
import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.appbase.utils.CipherUtils;

/**
 * API about user manipulation.
 */
public class UserAPImpl extends VolleyBaseAPImpl implements UserAPI {

    private static final String URL = Let.API_USER;

    public UserAPImpl(Context context) {
        super(context);
    }

    @Override
    public void register(String phone, String password, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("phone", phone);
        data.put("password", CipherUtils.encrypt(password, Let.DEFAULT_KEY));

        post(URL, data, cb, GeneralAO.class);
    }

    @Override
    public void save(UserAO user, String password, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("name", user.getName());
        data.put("phone", user.getPhone());
        data.put("ext", user.getExt());
        data.put("password", CipherUtils.encrypt(password, Let.DEFAULT_KEY));

        put(URL + "/" + user.getUid(), data, cb, GeneralAO.class);
    }

    @Override
    public void listGroup(Long uid, Callback<List<GroupAO>> cb) {
        String url = URL + "/" + uid + "/groups";
        getArray(url, cb, GroupAO.class, Let.DEFAULT_KEY);
    }

    @Override
    public void joinGroup(Long uid, String password, Long gid, String accessToken, Callback<GeneralAO> cb) {
        String url = URL + "/" + uid + "/groups";

        Map<String, String> data = new HashMap<>();
        data.put("password", CipherUtils.encrypt(password, Let.DEFAULT_KEY));
        data.put("gid", gid.toString());
        data.put("accessToken", CipherUtils.encrypt(accessToken, Let.DEFAULT_KEY));

        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void leaveGroup(Long uid, String password, Long gid, Callback<GeneralAO> cb) {
        String url = URL + "/" + uid + "/groups?gid=" + gid + "&password=" +
                CipherUtils.encrypt(password, Let.DEFAULT_KEY);

        delete(url, cb, GeneralAO.class);
    }

    @Override
    public void listFriend(Long uid, Callback<List<UserAO>> cb) {
        String url = URL + "/" + uid + "/friends";
        getArray(url, cb, UserAO.class, Let.DEFAULT_KEY);
    }

    @Override
    public void addFriend(Long uid, String password, String name, String phone, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("password", CipherUtils.encrypt(password, Let.DEFAULT_KEY));
        data.put("name", name);
        data.put("phone", phone);

        String url = URL + "/" + uid + "/friends";
        post(url, data, cb, GeneralAO.class);
    }

    @Override
    public void deleteFriend(Long uid, String password, Long fid, Callback<GeneralAO> cb) {
        String url = URL + "/" + uid + "/friends?fid=" + fid + "&password=" +
                CipherUtils.encrypt(password, Let.DEFAULT_KEY);
        delete(url, cb, GeneralAO.class);
    }

    @Override
    public void find(Long uid, Callback<List<UserAO>> cb) {
        String url = URL + "/" + uid;
        getArray(url, cb, UserAO.class, Let.DEFAULT_KEY);
    }

    public void setPassword(Long uid, String oldp, String newp, Callback<GeneralAO> cb) {
        Map<String, String> data = new HashMap<>();
        data.put("password", CipherUtils.encrypt(oldp, Let.DEFAULT_KEY));
        data.put("newpassword", CipherUtils.encrypt(newp, Let.DEFAULT_KEY));

        String url = URL + "/" + uid + "/password";
        put(url, data, cb, GeneralAO.class);
    }
}
