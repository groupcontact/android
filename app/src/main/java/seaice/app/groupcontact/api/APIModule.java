package seaice.app.groupcontact.api;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import seaice.app.groupcontact.ActionSheetActivity;
import seaice.app.groupcontact.AuthActivity;
import seaice.app.groupcontact.GroupCreateActivity;
import seaice.app.groupcontact.SearchActivity;
import seaice.app.groupcontact.StartupActivity;
import seaice.app.groupcontact.UserAddActivity;
import seaice.app.groupcontact.UserEditActivity;
import seaice.app.groupcontact.UserInfoActivity;
import seaice.app.groupcontact.UserListActivity;
import seaice.app.groupcontact.api.impl.ConfigAPImpl;
import seaice.app.groupcontact.api.impl.GroupAPImpl;
import seaice.app.groupcontact.api.impl.UserAPImpl;
import seaice.app.groupcontact.fragment.FriendListFragment;
import seaice.app.groupcontact.fragment.GroupListFragment;
import seaice.app.groupcontact.fragment.LoginFragment;
import seaice.app.groupcontact.fragment.ProfileFragment;
import seaice.app.groupcontact.fragment.RegisterFragment;

@Module(injects = {
        StartupActivity.class,
        SearchActivity.class,
        AuthActivity.class,
        GroupCreateActivity.class,
        UserListActivity.class,
        FriendListFragment.class,
        GroupListFragment.class,
        ProfileFragment.class,
        UserAddActivity.class,
        UserInfoActivity.class,
        UserEditActivity.class,
        ActionSheetActivity.class,
        LoginFragment.class,
        RegisterFragment.class
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
