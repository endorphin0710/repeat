package com.behemoth.repeat.stats.book;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookStatsModel implements BookStatsContract.Model {

    private final BookStatsContract.Presenter presenter;

    public BookStatsModel(BookStatsContract.Presenter p) {
        this.presenter = p;
    }

    @Override
    public void getBookInfo(String bookId) {
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