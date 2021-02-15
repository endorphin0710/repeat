package com.behemoth.repeat.recents;

import android.util.Log;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecentsModel implements RecentsContract.Model{

    private final RecentsContract.Presenter presenter;

    public RecentsModel(RecentsContract.Presenter p){
        this.presenter = p;
    }


    @Override
    public void getRecentMarks() {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(userId)
                .child("recentMarks");

        ArrayList<Mark> recents = new ArrayList<>();
        recentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot markSnapshot : dataSnapshot.getChildren()){
                    Mark m = (Mark)markSnapshot.getValue(Mark.class);
                    recents.add(m);
                }
                presenter.onRetrieveMarks(recents);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }
}
