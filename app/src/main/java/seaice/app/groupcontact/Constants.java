package seaice.app.groupcontact;

import java.util.HashMap;
import java.util.Map;

/**
 * Runtime Constants.
 * <p/>
 * Some annotations here: The difference between <code><b>SharedPreferences</b></code> and Runtime
 * Constants is: The first is used for persistence, the second is for runtime variable. To avoid
 * writing to preference file frequently, I store some attributes here(uid and name for example)
 *
 * @author zhb
 */
public class Constants {

    public static String baseUrl;

    public static Long uid = -1L;

    public static String name = "";

    public static String password = "";

    public static Map<Long, String> accessTokens = new HashMap<>();

    public static final String DEFAULT_KEY = "groupcontact";

}
