package seaice.app.groupcontact.api.impl;

import android.content.Context;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.ConfigAPI;
import seaice.app.groupcontact.api.ao.ConfigAO;

public class ConfigAPImpl extends VolleyBaseAPImpl implements ConfigAPI {

    private static final String url = "http://groupcontact.duapp.com/android.json";

    public ConfigAPImpl(Context context) {
        super(context);
    }

    @Override
    public void load(Callback<ConfigAO> cb) {
        get(url, cb, ConfigAO.class, null);
    }
}
