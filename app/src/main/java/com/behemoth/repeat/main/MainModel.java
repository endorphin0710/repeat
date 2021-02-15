package com.behemoth.repeat.main;

import android.util.Log;

import androidx.annotation.NonNull;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Mark;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
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

public class MainModel implements MainContract.Model{

    private final MainContract.Presenter presenter;

    public MainModel(MainContract.Presenter p){
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

    @Override
    public void deleteBook(int position, Book book) {
        String userId = book.getAuthor();
        String bookId = book.getId();
        String imageName = book.getImageName();
        FirebaseDatabase.getInstance().getReference().child("book").child(userId).child(bookId).removeValue()
            .addOnSuccessListener(aVoid -> {
                if (imageName != null) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    storage.setMaxUploadRetryTimeMillis(Constants.MAX_UPLOAD_RETRY_MILLIS);
                    StorageReference storageRef = storage.getReference();
                    StorageReference imageRef = storageRef.child("images/"+imageName);
                    imageRef.delete();
                }

                List<Mark> marks = new ArrayList<>();
                DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference().child("user").child(userId).child("recentMarks");
                recentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot markSnapshot : dataSnapshot.getChildren()){
                            Mark m = (Mark)markSnapshot.getValue(Mark.class);
                            if(m == null || m.getId().equals(bookId)) continue;
                            marks.add(m);
                        }

                        recentRef.setValue(marks).addOnSuccessListener(v -> {
                            presenter.onDeleteSuccess(position);
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            });
    }

}
