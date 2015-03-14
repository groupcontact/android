package seaice.app.groupcontact.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.StartupActivity;
import seaice.app.groupcontact.UserCreateActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.api.impl.ConfigAPImpl;
import seaice.app.groupcontact.api.impl.GroupAPImpl;
import seaice.app.groupcontact.api.impl.UserAPImpl;
import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;

@Module(injects = {
        StartupActivity.class,
        SearchActivity.class,
        UserCreateActivity.class,
        UserListActivity.class,
        FriendListFragment.class,
        GroupListFragment.class,
        ProfileFragment.class
})
public class APIModule {

    private Context mContext;

    public APIModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public ConfigAPI provideConfigAPI() {
        return new ConfigAPImpl(mContext);
    }

    @Provides
    @Singleton
    public GroupAPI provideGroupAPI() {
        return new GroupAPImpl(mContext);
    }

    @Provides
    @Singleton
    public UserAPI provideUserAPI() {
        return new UserAPImpl(mContext);
    }

}
