package seaice.app.groupcontact;

import java.util.HashMap;
import java.util.Map;

import seaice.app.groupcontact.api.ao.ConfigAO;
import seaice.app.groupcontact.api.ao.UserAO;

/**
 * Runtime Constants.
 * <p/>
 * Some annotations here: The difference between <code><b>SharedPreferences</b></code> and Runtime
 * Constants is: The first is used for persistence, the second is for runtime variable. To avoid
 * writing to preference file frequently, I store some attributes here(uid and name for example)
 *
 * @author zhb
 */
public class Var {

    public static ConfigAO config;

    public static UserAO userAO;

    public static Long uid = -1L;

    public static String name = "";

    public static String password = "";

}
