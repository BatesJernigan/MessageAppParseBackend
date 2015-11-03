package com.parse.starter;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.SingleLaunchActivityTestCase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    public void signUpUser(View view) {
        EditText emailField = (EditText) findViewById(R.id.signUpEmail);
        EditText passwordField = (EditText) findViewById(R.id.signUpPasswordInput);
        EditText passwordConfirmationField = (EditText) findViewById(R.id.signUpPasswordConfirmation);
        EditText fullNameField = (EditText) findViewById(R.id.fullName);

        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();
        final String passwordConfirmation = passwordConfirmationField.getText().toString();
        final String fullName = fullNameField.getText().toString();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        Log.d("demo", "email: " + email);
        query.whereEqualTo("email", email);
        if(!passwordConfirmation.equals(password)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else if(email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(this, "All Fields are required.", Toast.LENGTH_LONG).show();
        } else {
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size() > 0) {
                        Toast.makeText(SignUpActivity.this, "Email Already Exists.", Toast.LENGTH_LONG).show();
                    } else {
                        ParseUser user = new ParseUser();
                        user.setUsername(email);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.put("fullName", fullName);

                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(SignUpActivity.this, "Signed Up Successfully!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, MessagesActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.d("demo", "signup didn't work :( " + e);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void cancelSignUp(View view) {
        finish();
    }
}
