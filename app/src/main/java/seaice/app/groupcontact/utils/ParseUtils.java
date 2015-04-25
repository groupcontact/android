package seaice.app.groupcontact.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import seaice.app.groupcontact.api.ao.ConfigAO;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.GroupAO;
import seaice.app.groupcontact.api.ao.UserAO;

public class ParseUtils {

    public static <T> String toString(T ao, Class<T> typedClass) {
        JSONObject obj = toJSON(ao, typedClass);
        if (obj == null) {
            return "{}";
        } else {
            return obj.toString();
        }
    }

    public static <T> String toListString(List<T> aoList, Class<T> typedClass) {
        return toJSONArray(aoList, typedClass).toString();
    }

    public static <T> JSONObject toJSON(T ao, Class<T> typedClass) {
        if (typedClass.equals(GeneralAO.class)) {
            return GeneralAO.toJSON((GeneralAO) ao);
        } else if (typedClass.equals(UserAO.class)) {
            return UserAO.toJSON((UserAO) ao);
        } else if (typedClass.equals(GroupAO.class)) {
            return GroupAO.toJSON((GroupAO) ao);
        } else if (typedClass.equals(ConfigAO.class)) {
            return ConfigAO.toJSON((ConfigAO) ao);
        }
        return null;
    }

    public static <T> JSONArray toJSONArray(List<T> aoList, Class<T> typedClass) {
        JSONArray array = new JSONArray();
        for (T ao : aoList) {
            if (typedClass.equals(GeneralAO.class)) {
                array.put(GeneralAO.toJSON((GeneralAO) ao));
            } else if (typedClass.equals(UserAO.class)) {
                array.put(UserAO.toJSON((UserAO) ao));
            } else if (typedClass.equals(GroupAO.class)) {
                array.put(GroupAO.toJSON((GroupAO) ao));
            } else if (typedClass.equals(ConfigAO.class)) {
                array.put(ConfigAO.toJSON((ConfigAO) ao));
            }
        }
        return array;
    }

    public static <T> T fromJSON(JSONObject source, Class<T> typedClass) {
        if (typedClass.equals(GeneralAO.class)) {
            return (T) GeneralAO.fromJSON(source);
        } else if (typedClass.equals(UserAO.class)) {
            return (T) UserAO.fromJSON(source);
        } else if (typedClass.equals(GroupAO.class)) {
            return (T) GroupAO.fromJSON(source);
        } else if (typedClass.equals(ConfigAO.class)) {
            return (T) ConfigAO.fromJSON(source);
        }
        return null;
    }

    public static <T> List<T> fromJSONArray(JSONArray array, Class<T> typedClass) {
        List<T> data = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); ++i) {
                data.add(fromJSON(array.getJSONObject(i), typedClass));
            }
        } catch (JSONException e) {
        }
        return data;
    }

    public static <T> T fromString(String str, Class<T> typedClass) {
        try {
            JSONObject source = new JSONObject(str);
            return fromJSON(source, typedClass);
        } catch (JSONException e) {
            return null;
        }
    }

    public static <T> List<T> fromListString(String str, Class<T> typedClass) {
        List<T> data = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(str);
            return fromJSONArray(array, typedClass);
        } catch (JSONException e) {
        }
        return data;
    }
}
