package seaice.app.groupcontact.api.ao;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Configuration API Object:
 *
 * @author zhb
 */
public class ConfigAO {

    private String baseUrl;

    public static ConfigAO fromJSON(JSONObject obj) {
        ConfigAO config = new ConfigAO();
        config.setBaseUrl(obj.optString("baseUrl", ""));

        return config;
    }

    public static JSONObject toJSON(ConfigAO ao) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("baseUrl", ao.getBaseUrl());
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
