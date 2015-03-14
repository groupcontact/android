package seaice.app.groupcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DaggerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.inject(this);
    }
}
