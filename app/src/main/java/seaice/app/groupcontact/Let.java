package seaice.app.groupcontact;

import android.os.Environment;

public class Let {

    public static final String DEFAULT_KEY = "groupcontact";

    public static final String API_USER = "http://groupcontact.duapp.com/api/v2/users";

    public static final String API_GROUP = "http://groupcontact.duapp.com/api/v2/groups";

    public static final String FRIEND_CACHE_PATH = "friend.s";

    public static final String GROUP_CACHE_PATH = "group.s";

    public static final String PROFILE_CACHE_PATH = "profile.s";

    public static final String APP_DIR = Environment.getExternalStorageDirectory() + "/GroupContact/";

    public static final int APP_VERSION = 3;
}