package seaice.app.groupcontact.api.ao;

/**
 * The User API Object:
 *
 * Created by zhb on 3/5/15.
 */
public class UserAO  {

    private Long uid;

    private String name;

    private String phone;

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
}
