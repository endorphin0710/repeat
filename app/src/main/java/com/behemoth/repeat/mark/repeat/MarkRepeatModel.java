package com.behemoth.repeat.mark.repeat;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public void mark(Book b, int chapterNumber, boolean finished, int score, int problemCnt) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        Chapter c = b.getChapter().get(chapterNumber);
        int repeatCount = c.getRepeatCount()-1;

        if(finished) {
            c.setRepeatCount(c.getRepeatCount()+1);
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

            DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(userId)
                    .child("recentMarks");

            int usingThumbnail = b.getIsUsingThumbnail();
            String thumbnail = "";
            String imageName = "";
            if(usingThumbnail > 0){
                thumbnail = b.getThumbnail();
            }else{
                imageName = b.getImageName();
            }
            Mark mark = new Mark(b.getId(), b.getTitle(), chapterNumber, repeatCount, score, problemCnt, System.currentTimeMillis(), usingThumbnail, thumbnail, imageName);

            List<Mark> recentMarks = new ArrayList<>();
            recentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot markSnapshot : dataSnapshot.getChildren()){
                        Mark m = (Mark)markSnapshot.getValue(Mark.class);
                        recentMarks.add(m);
                    }

                    recentMarks.add(0, mark);
                    int cnt = recentMarks.size();
                    if(cnt > Constants.LIMIT_RECENT_MARKS){
                        recentMarks.remove(Constants.LIMIT_RECENT_MARKS);
                    }

                    recentRef.setValue(recentMarks);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
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
                    presenter.onUpdateFailure();
                });

    }

    @Override
    public void markTemp(Book b, int chapterNumber, int score, int problemCnt) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        Chapter c = b.getChapter().get(chapterNumber);
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
                    presenter.onUpdateFailure();
                });
    }

    public void getBook(String bookId){
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("book").child(userId).child(bookId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    return;
                }
                Book book = dataSnapshot.getValue(Book.class);
                presenter.onRetrieveBook(book);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


}
