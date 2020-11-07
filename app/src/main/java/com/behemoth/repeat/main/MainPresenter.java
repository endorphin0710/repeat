package com.behemoth.repeat.main;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View view;

    public MainPresenter(MainContract.View view){
        this.view = view;
    }

    @Override
    public void getBooks() {
        final String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        final List<Book> books = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    view.onRetrieveBook(books);
                    return;
                }
                for(DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    Book book = bookSnapshot.getValue(Book.class);
                    if(book.getAuthor().equals(userId)) books.add(book);
                }
                view.onRetrieveBook(books);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.onRetrieveBook(books);
            }
        });
    }

    @Override
    public void onClick(int position) {
        if(position == 0){
            view.newBook();
        }else{

        }
    }

}
