package seaice.app.groupcontact.api.ao;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * The User API Object.
 *
 * @author zhb
 */
public class UserAO implements Parcelable {

    public static final Parcelable.Creator<UserAO> CREATOR = new Creator<UserAO>() {

        @Override
        public UserAO createFromParcel(Parcel source) {
            UserAO user = new UserAO();
            user.setUid(source.readLong());
            user.setName(source.readString());
            user.setPhone(source.readString());
            user.setExt(source.readString());

            return user;
        }

        @Override
        public UserAO[] newArray(int size) {
            return new UserAO[size];
        }
    };
    private Long uid;
    private String name;
    private String phone;
    private String ext;

    public static UserAO parse(JSONObject obj) {
        UserAO user = new UserAO();
        user.setUid(obj.optLong("id", -1L));
        user.setName(obj.optString("name", ""));
        user.setPhone(obj.optString("phone", ""));
        user.setExt(obj.optString("ext", "{}"));

        return user;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(ext);
    }
}
