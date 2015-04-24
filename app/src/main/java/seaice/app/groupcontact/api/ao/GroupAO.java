package seaice.app.groupcontact.api.ao;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Group API Object.
 * <p/>
 * Created by zhb on 3/5/15.
 */
public class GroupAO implements Parcelable {

    public static final Parcelable.Creator<GroupAO> CREATOR = new Creator<GroupAO>() {

        @Override
        public GroupAO createFromParcel(Parcel source) {
            GroupAO group = new GroupAO();
            group.setGid(source.readLong());
            group.setName(source.readString());
            group.setDesc(source.readString());
            return group;
        }

        @Override
        public GroupAO[] newArray(int size) {
            return new GroupAO[size];
        }
    };
    private Long gid;
    private String name;
    private String desc;

    private String accessToken;
    private String modifyToken;

    public static GroupAO parse(JSONObject obj) {
        GroupAO group = new GroupAO();
        group.setGid(obj.optLong("id", -1L));
        group.setName(obj.optString("name", ""));
        group.setDesc(obj.optString("desc", ""));

        return group;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", gid);
        obj.put("name", name);
        obj.put("desc", desc);

        return obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getModifyToken() {
        return modifyToken;
    }

    public void setModifyToken(String modifyToken) {
        this.modifyToken = modifyToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(gid);
        dest.writeString(name);
        dest.writeString(desc);
    }
}
