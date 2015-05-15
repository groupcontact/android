package seaice.app.groupcontact;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

import dagger.ObjectGraph;
import seaice.app.groupcontact.api.APIModule;

public class MyApplication extends Application {

    private static ObjectGraph objectGraph;

    public static void inject(Object obj) {
        objectGraph.inject(obj);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new APIModule(this));

        MobclickAgent.openActivityDurationTrack(false);
    }
}
