package seaice.app.groupcontact;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DaggerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dagger need this for injection
        MyApplication.inject(this);
    }
}
