package com.behemoth.repeat.mark;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarkModel implements MarkContract.Model{

    private final MarkContract.Presenter presenter;

    public MarkModel(MarkContract.Presenter p){
        this.presenter = p;
    }

    @Override
    public void getBooks() {
        final String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        final List<Book> books = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    presenter.onRetrieveBook(books);
                    return;
                }
                for(DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    Book book = bookSnapshot.getValue(Book.class);
                    if(book != null && book.getAuthor().equals(userId)) books.add(book);
                }
                presenter.onRetrieveBook(books);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                presenter.onRetrieveBook(books);
            }
        });
    }

}
