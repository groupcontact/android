package seaice.app.groupcontact;

import android.os.Environment;

public class Let {

    public static final String DEFAULT_KEY = "groupcontact";

    public static final String API_USER = "http://groupcontact.duapp.com/api/v3/users";

    public static final String API_GROUP = "http://groupcontact.duapp.com/api/v3/groups";

    public static final String FRIEND_CACHE_PATH = "friend.s";

    public static final String GROUP_CACHE_PATH = "group.s";

    public static final String PROFILE_CACHE_PATH = "profile.s";

    public static final String APP_DIR = Environment.getExternalStorageDirectory() + "/GroupContact/";

    public static final int APP_VERSION = 3;

    public static final int REQUEST_CODE_INIT_DATA = 1;

    public static final int REQUEST_CODE_ADD_FRIEND = 2;

    public static final int REQUEST_CODE_VIEW_GROUP = 3;

    public static final int REQUEST_CODE_VIEW_FRIEND = 4;

    public static final int REQUEST_CODE_CREATE_GROUP = 5;

    public static final int REQUEST_CODE_JOIN_GROUP = 6;

    public static final int REQUEST_CODE_OPERATE_PHONE = 7;

    public static final int REQUEST_CODE_OPERATE_EMAIL = 8;
}
