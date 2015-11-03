/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.net.Inet4Address;


public class MainActivity extends ActionBarActivity {

  EditText emailField, passwordField;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

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

  public void signUpForParse(View view) {
    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
    startActivity(intent);
  }

  public void loginUser(View view) {
    emailField = (EditText) findViewById(R.id.loginEmailInput);
    passwordField = (EditText) findViewById(R.id.loginPasswordInput);
    String email = emailField.getText().toString();
    String password = passwordField.getText().toString();

    if(email.isEmpty() || password.isEmpty()) {
      Toast.makeText(this, "Email and Password required", Toast.LENGTH_LONG).show();
    }

    ParseUser.logInInBackground(email, password, new LogInCallback() {
      public void done(ParseUser user, ParseException e) {
        if (user != null) {
          Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_LONG).show();
          Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(MainActivity.this, "Incorrect Email/Password combo", Toast.LENGTH_LONG).show();
          Log.d("demo", "user is not logged :(");
        }
      }
    });
  }
}
