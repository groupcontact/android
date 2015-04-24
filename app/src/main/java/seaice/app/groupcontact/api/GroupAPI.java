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

    public void create(Long uid, String password, GroupAO group, Callback<GeneralAO> cb);

    public void list(Long gid, Callback<List<UserAO>> cb);

    public void search(String name, Callback<List<GroupAO>> cb);
}
