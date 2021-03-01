package com.behemoth.repeat.mypage;

import androidx.annotation.NonNull;

import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPageModel implements MyPageContract.Model {

    private final MyPageContract.Presenter presenter;

    private String nickName;
    private String versionName;

    public MyPageModel(MyPageContract.Presenter p) {
        this.presenter = p;
    }

    @Override
    public void getData() {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("user").child(userId).child("nickName");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nickName = (String) dataSnapshot.getValue();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("versionName");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        versionName = (String)dataSnapshot.getValue();
                        presenter.onRetrieveData(nickName, versionName);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

}