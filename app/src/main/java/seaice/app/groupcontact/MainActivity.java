package seaice.app.groupcontact;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.ao.ConfigAO;
import seaice.app.groupcontact.api.impl.ConfigAPImpl;


public class MainActivity extends ActionBarActivity {

    private TextView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTestView = (TextView) findViewById(R.id.test);

        new ConfigAPImpl().load(this, null, new Callback<ConfigAO>() {

            @Override
            public void call(ConfigAO result) {
                mTestView.setText(result.getBaseUrl());
            }

            @Override
            public void error(String message) {
                mTestView.setText(message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
