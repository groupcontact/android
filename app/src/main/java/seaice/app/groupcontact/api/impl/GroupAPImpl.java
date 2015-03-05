package seaice.app.groupcontact.api.impl;

import android.content.Context;

import java.util.List;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.GroupAPI;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Created by zhb on 3/5/15.
 */
public class GroupAPImpl implements GroupAPI {

    @Override
    public void create(Context context, GroupAO group, Callback<GeneralAO> cb) {

    }

    @Override
    public void delete(Context context, GroupAO group, String modifyToken, Callback<GeneralAO> cb) {

    }

    @Override
    public void update(Context context, GroupAO group, String modifyToken, Callback<GeneralAO> cb) {

    }

    @Override
    public void list(Context context, String gid, String accessToken, Callback<List<UserAO>> cb) {

    }
}
