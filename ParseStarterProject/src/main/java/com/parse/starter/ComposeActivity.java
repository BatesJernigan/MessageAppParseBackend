package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
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

    public void createMessage(View view) {

        EditText messageTextBox = (EditText) findViewById(R.id.messageTextBox);
        String message = messageTextBox.getText().toString();

        if(message.isEmpty()) {
            Toast.makeText(ComposeActivity.this, "Text is required for a message.", Toast.LENGTH_LONG).show();
        } else {
            final ParseObject messageObj = new ParseObject("Message");
            messageObj.put("content", message);
            messageObj.put("createdBy", ParseUser.getCurrentUser());

            messageObj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(ComposeActivity.this, "Message saved correctly!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        // The save failed.
                        Log.d("demo", "Message save error: " + e);
                    }
                }
            });
        }
    }
}
