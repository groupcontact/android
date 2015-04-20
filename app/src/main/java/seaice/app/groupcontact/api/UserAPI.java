package seaice.app.groupcontact.api;

import android.content.Context;

import java.util.List;

import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public interface UserAPI {

    /**
     * Register a User Instance.
     *
     * @param phone
     * @param password
     * @param cb
     * @since v2
     */
    public void register(String phone, String password, Callback<GeneralAO> cb);

    public void save(UserAO user, String password, Callback<GeneralAO> cb);

    public void listGroup(Long uid, Callback<List<GroupAO>> cb);

    public void joinGroup(Long uid, String password, Long gid, String accessToken, Callback<GeneralAO> cb);

    public void deleteGroup(Long uid, String password, Long gid, String accessToken, Callback<GeneralAO> cb);

    public void listFriend(Long uid, Callback<List<UserAO>> cb);

    public void addFriend(Long uid, String fname, String fphone, Callback<GeneralAO> cb);

    public void deleteFriend(Long uid, String name, Long fid, Callback<GeneralAO> cb);

    public void find(Long uid, Callback<List<UserAO>> cb);
}
