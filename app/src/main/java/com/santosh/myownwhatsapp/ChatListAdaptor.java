package com.santosh.myownwhatsapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.santosh.myownwhatsapp.model.ChatMsg;

import java.util.ArrayList;

public class ChatListAdaptor extends BaseAdapter {
    private final String TAG = "CHAT LIST ADAPTOR";
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
            Log.d(TAG,"Received a chat message in adaptor ");
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            //TODO : Handle changes to existing chat
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            //TODO : Handle removal of existing chat
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG,"Cannot listen to DB changes. Please check if you have requried permission : "+databaseError);
        }
    };

    public ChatListAdaptor(Activity mActivity, DatabaseReference mDatabaseReference) {
        this.mActivity = mActivity;
        this.mDatabaseReference = mDatabaseReference.child("messages");
        this.mDatabaseReference.addChildEventListener(mChildListener);
        mSnapshotList = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView msgTextView;
        TextView userTextView;

    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public ChatMsg getItem(int i) {
        DataSnapshot snapShot =  mSnapshotList.get(i);
        return snapShot.getValue(ChatMsg.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view ==null) //didnt get reusable view, create a new one
        {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.chat_row,viewGroup,false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.msgTextView = view.findViewById(R.id.msgTextView);
            viewHolder.userTextView = view.findViewById(R.id.userNameTextView);

            view.setTag(viewHolder);
        }

        ChatMsg chatMsg = getItem(i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.msgTextView.setText(chatMsg.getMsg());
        holder.userTextView.setText(chatMsg.getUser());

        return view;
    }
}
