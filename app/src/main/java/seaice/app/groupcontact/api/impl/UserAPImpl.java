package seaice.app.groupcontact.api.impl;

import android.content.Context;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.UserAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public class UserAPImpl implements UserAPI {

    @Override
    public void register(Context context, UserAO user, Callback<GeneralAO> cb) {

    }

    @Override
    public void update(Context context, UserAO user, Callback<GeneralAO> cb) {

    }

    @Override
    public void join(Context context, Long uid, Long gid, String accessToken, Callback<GeneralAO> cb) {

    }

    @Override
    public void leave(Context context, Long uid, Long gid, Callback<GeneralAO> cb) {

    }
}
