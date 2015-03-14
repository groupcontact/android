package seaice.app.groupcontact.api.ao;

import org.json.JSONObject;

/**
 * The Configuration API Object:
 *
 * @author zhb
 */
public class ConfigAO {

    private String baseUrl;

    public static ConfigAO parse(JSONObject obj) {
        ConfigAO config = new ConfigAO();
        config.setBaseUrl(obj.optString("baseUrl", ""));

        return config;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
