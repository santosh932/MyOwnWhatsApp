package com.santosh.myownwhatsapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santosh.myownwhatsapp.model.ChatMsg;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private  DatabaseReference msgDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_new_window);

        final TextView msgTextView = findViewById(R.id.msgEditText);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        msgDatabaseRef = database.getReference("messages");

        ListView chatListView = findViewById(R.id.chatListView);
        chatListView.setAdapter(new ChatListAdaptor(this,database.getReference()));

        findViewById(R.id.SendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msgTextView.getText().toString();

                msgTextView.setText(""); //clear the message.

                //create and initialize model class
                ChatMsg chatMsg = new ChatMsg(msg,"santosh",new Date());
                Log.d("CHAT","sending message to firebase :: "+chatMsg);

                String msgId = msgDatabaseRef.push().getKey();
                msgDatabaseRef.child(msgId).setValue(chatMsg);
            }
        });

    }

}
