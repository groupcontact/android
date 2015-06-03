package seaice.app.groupcontact;

import seaice.app.appbase.BaseApplication;
import seaice.app.groupcontact.api.APIModule;

public class MyApplication extends BaseApplication {
    @Override
    public Object[] getModules() {
        return new Object[]{new APIModule(this)};
    }
}
