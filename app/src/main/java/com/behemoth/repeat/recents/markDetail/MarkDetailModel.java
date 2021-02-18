package com.behemoth.repeat.recents.markDetail;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.model.Problem;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarkDetailModel implements MarkDetailContract.Model{

    private final MarkDetailContract.Presenter presenter;

    public MarkDetailModel(MarkDetailContract.Presenter p){
        this.presenter = p;
    }

    @Override
    public void getMarkDetail(Mark m) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference()
                .child("book")
                .child(userId)
                .child(m.getId())
                .child("chapter")
                .child(String.valueOf(m.getChatper()))
                .child("repeat")
                .child(String.valueOf(m.getRepeat()))
                .child("mark");

        recentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 1;
                List<Problem> problems = new ArrayList<>();
                for(DataSnapshot markSnapshot : snapshot.getChildren()){
                    int s = markSnapshot.getValue(Integer.class);
                    problems.add(new Problem(i, s));
                    i += 1;
                }

                presenter.onRetrieveMarkDetail(problems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
