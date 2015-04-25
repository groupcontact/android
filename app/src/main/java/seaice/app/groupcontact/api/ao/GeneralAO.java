package seaice.app.groupcontact.api.ao;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The General API Object.
 *
 * @author zhb
 */
public class GeneralAO {

    private Integer status;

    private String info;

    private Long id;

    public static GeneralAO fromJSON(JSONObject obj) {
        GeneralAO result = new GeneralAO();

        result.setStatus(obj.optInt("status", -1));
        result.setId(obj.optLong("id", 0L));
        result.setInfo(obj.optString("info", ""));

        return result;
    }

    public static JSONObject toJSON(GeneralAO ao) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("status", ao.getStatus());
            obj.put("info", ao.getInfo());
            obj.put("id", ao.getInfo());
            return obj;
        } catch (JSONException e) {
            return null;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
