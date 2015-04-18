package seaice.app.groupcontact.api;

import android.content.Context;

import java.util.List;

import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public interface GroupAPI {

    public void create(String name, String desc, String access, String modify, Callback<GeneralAO> cb);

    public void list(Long gid, String accessToken, Callback<List<UserAO>> cb);

    public void search(String name, Callback<List<GroupAO>> cb);

    public void join(Long uid, Long gid, String accessToken, Callback<GeneralAO> cb);

    public void leave(Long uid, Long gid, Callback<GeneralAO> cb);
}
