package seaice.app.groupcontact.api;

import android.content.Context;

import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public interface UserAPI {

    public void register(Context context, UserAO user, Callback<GeneralAO> cb);

    public void update(Context context, UserAO user, Callback<GeneralAO> cb);

    public void join(Context context, Long uid, Long gid, String accessToken, Callback<GeneralAO> cb);

    public void leave(Context context, Long uid, Long gid, Callback<GeneralAO> cb);
}
