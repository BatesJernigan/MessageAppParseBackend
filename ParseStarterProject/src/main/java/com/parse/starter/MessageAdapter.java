package com.parse.starter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by batesjernigan on 11/2/15.
 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {

    Context mContext;
    int mResource;
    List<ParseObject> mData;

    public MessageAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        final ParseObject message = mData.get(position);
        TextView fullName = (TextView) convertView.findViewById(R.id.messagePartialFullName);
        TextView messageContent = (TextView) convertView.findViewById(R.id.messageContent);
        TextView dateField = (TextView) convertView.findViewById(R.id.createdAt);
        ImageView deleteImage = (ImageView) convertView.findViewById(R.id.deleteImage);

        ParseUser user = message.getParseUser("createdBy");
        fullName.setText(user.get("fullName").toString());
        messageContent.setText(message.get("content").toString());
        dateField.setText(message.getCreatedAt().toString());

        if(user.equals(ParseUser.getCurrentUser())) {
            //Drawable image = mContext.getDrawable(R.drawable.bin);

            Drawable image = mContext.getResources().getDrawable(R.drawable.bin);
            //mContext.getDrawable(R.drawable.bin);
            deleteImage.setImageDrawable(image);
        }
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.deleteInBackground();
            }
        });
        return convertView;
    }
}

