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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        }

        // firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("book")
                .child(userId)
                .child(b.getId())
                .child("chapter");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(String.valueOf(chapterNumber), c);

        ref.updateChildren(childUpdates)
                .addOnSuccessListener(aVoid -> {
                    presenter.onUpdateSuccess();
                })
                .addOnFailureListener(e -> {
                    Log.d("juntae", "Failure");
                });

    }


}
