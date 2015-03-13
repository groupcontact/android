package seaice.app.groupcontact.api;

import android.content.Context;

import seaice.app.groupcontact.api.ao.ConfigAO;

/**
 * Created by zhb on 3/5/15.
 */
public interface ConfigAPI {

    /**
     * Load configuration with default value <code>customized</code>
     *
     * @param cb
     * @return
     */
    public void load(Callback<ConfigAO> cb);

}
