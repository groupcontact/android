package seaice.app.groupcontact.api;

/**
 * Created by zhb on 3/5/15.
 */
public interface Callback<AO> {

    public void call(AO result);

    public void error(String message);

}
