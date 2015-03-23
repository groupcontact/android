package seaice.app.groupcontact.api;

import seaice.app.groupcontact.api.ao.ConfigAO;

public interface ConfigAPI {

    /**
     * Load configurations
     *
     * @param cb
     * @return
     */
    public void load(Callback<ConfigAO> cb);

}
