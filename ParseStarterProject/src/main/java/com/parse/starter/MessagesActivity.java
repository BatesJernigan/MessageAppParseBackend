package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    List<ParseObject> messages;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        listView = (ListView) findViewById(R.id.messagesListView);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ParseObject message = messages.get(position);
//                message.deleteInBackground(new DeleteCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null) {
//                            Log.d("demo", "deleted successfully");
//                        } else {
//                            Log.d("demo", "deleted not successfully");
//                        }
//
//                    }
//                });
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessages();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refreshOption) {
            getAllMessages();
            return true;
        } else if (id == R.id.compose_message) {
            Intent intent = new Intent(MessagesActivity.this, ComposeActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.logoutOption) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    finish();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    public void getAllMessages() {
        Log.d("demo", "called get all messages");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.include("createdBy");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("demo", "query find in background done " + objects);
                for (ParseObject message : objects) {
                    Log.d("demo", "objects retrieved " + message.get("content").toString());
                }
                messages = objects;
                MessageAdapter adapter = new MessageAdapter(MessagesActivity.this, R.layout.message_layout, objects);
                listView.setAdapter(adapter);
                adapter.setNotifyOnChange(true);
            }
        });
    }
}
