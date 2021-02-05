package com.behemoth.repeat.mark.repeat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkRepeatModel implements MarkRepeatContract.Model{

    private final MarkRepeatContract.Presenter presenter;

    public MarkRepeatModel(MarkRepeatContract.Presenter p){
        this.presenter = p;
    }

    @Override
    public void mark(Book b, int chapterNumber, boolean finished) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        Chapter c = b.getChapter().get(chapterNumber);
        int repeatCount = c.getRepeatCount();

        if(finished) {
            c.setRepeatCount(repeatCount+1);
            List<Repeat> repeats = c.getRepeat();

            Repeat newRepeat = new Repeat();
            newRepeat.setProblemCount(c.getProblemCount());
            newRepeat.setFinished(false);

            List<Integer> marks = new ArrayList<>();
            for(int i = 0; i < c.getProblemCount(); i++){
                marks.add(-1);
            }
            newRepeat.setMark(marks);

            repeats.add(newRepeat);

            String mostRecent = chapterNumber+"/"+(repeatCount-1);
            DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference()
                    .child("book")
                    .child(userId)
                    .child(b.getId())
                    .child("recentMarks");

            List<String> recentMarks = new ArrayList<>();
            recentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("juntae", "count : " + dataSnapshot.getChildrenCount());
                    for(DataSnapshot markSnapshot : dataSnapshot.getChildren()){
                        String mark = (String)markSnapshot.getValue();
                        recentMarks.add(mark);
                    }

                    recentMarks.add(0, mostRecent);
                    int cnt = recentMarks.size();
                    if(cnt > Constants.LIMIT_RECENT_MARKS){
                        recentMarks.remove(Constants.LIMIT_RECENT_MARKS);
                    }

                    recentRef.setValue(recentMarks);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }

        // firebase
        DatabaseReference markRef = FirebaseDatabase.getInstance().getReference()
                .child("book")
                .child(userId)
                .child(b.getId())
                .child("chapter");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.valueOf(chapterNumber), c);

        markRef.updateChildren(childUpdates)
                .addOnSuccessListener(aVoid -> {
                    presenter.onUpdateSuccess();
                })
                .addOnFailureListener(e -> {

                });

    }


}
