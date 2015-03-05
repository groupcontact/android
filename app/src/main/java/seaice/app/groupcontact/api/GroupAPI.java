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

    public void create(Context context, GroupAO group, Callback<GeneralAO> cb);

    public void delete(Context context, GroupAO group, String modifyToken, Callback<GeneralAO> cb);

    public void update(Context context, GroupAO group, String modifyToken, Callback<GeneralAO> cb);

    public void list(Context context, String gid, String accessToken, Callback<List<UserAO>> cb);
}
