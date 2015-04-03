package seaice.app.groupcontact.api;

public interface Callback<AO> {

    public void call(AO result);

    public void info(String message);

}
