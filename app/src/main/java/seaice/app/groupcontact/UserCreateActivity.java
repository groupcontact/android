package seaice.app.groupcontact;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import seaice.app.groupcontact.api.Callback;
import seaice.app.groupcontact.api.ao.GeneralAO;
import seaice.app.groupcontact.api.ao.UserAO;
import seaice.app.groupcontact.api.impl.UserAPImpl;

/**
 * create a user instance in the app..
 *
 * @author zhb
 */
public class UserCreateActivity extends ActionBarActivity {

    private EditText mNameView;

    private EditText mPhoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create);

        mNameView = (EditText)findViewById(R.id.user_create_name);
        mPhoneView = (EditText) findViewById(R.id.user_create_phone);

        Button btnCreate = (Button) findViewById(R.id.user_create_create);
        final Context context = this;
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAO user = new UserAO();
                user.setName(mNameView.getText().toString());
                user.setPhone(mPhoneView.getText().toString());
                new UserAPImpl(context).create(user, new Callback<GeneralAO>() {

                    @Override
                    public void call(GeneralAO result) {
                        // failed to load resource
                        if (result.getStatus() == -1) {
                            Toast.makeText(context, result.getInfo(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        // yes, the user logged in
                        SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                        prefs.edit().putLong("uid", result.getId()).commit();
                        // goes to the main activity
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("uid", result.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void error(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_create, menu);
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
