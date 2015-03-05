package seaice.app.groupcontact.api.ao;

/**
 * The Group API Object:
 *
 * Created by zhb on 3/5/15.
 */
public class GroupAO {

    private Long gid;

    private String name;

    private String desc;

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
}
