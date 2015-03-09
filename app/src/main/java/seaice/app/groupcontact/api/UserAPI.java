package seaice.app.groupcontact.api;

import android.content.Context;

import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public interface UserAPI {

    public void create(UserAO user, Callback<GeneralAO> cb);

    public void edit(UserAO user, Callback<GeneralAO> cb);

    public void join(Long uid, Long gid, String accessToken, Callback<GeneralAO> cb);

    public void leave(Long uid, Long gid, Callback<GeneralAO> cb);
}
