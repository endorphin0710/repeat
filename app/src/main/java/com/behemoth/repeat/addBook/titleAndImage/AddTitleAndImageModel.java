package com.behemoth.repeat.addBook.titleAndImage;

import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddTitleAndImageModel extends AppCompatActivity implements AddTitleAndImageContract.Model {

    private static final String TAG = "AddTitleAndImageModel";

    private AddTitleAndImageContract.Presenter presenter;

    public AddTitleAndImageModel(AddTitleAndImageContract.Presenter p){
        this.presenter = p;
    }

    @Override
    public StorageReference getImageReference(String name) {
        return FirebaseStorage.getInstance().getReference().child("images/"+name);
    }

    @Override
    public void updateTitleAndImage(Book book, Uri bookUri,  String title, boolean isOriginal) {
        Log.d("juntae", book.toString());
        Log.d("juntae", "bookUri : " + bookUri);
        Log.d("juntae", "title : " + title);
        Log.d("juntae", "isOriginal : " + isOriginal);
        boolean updateTitle = !book.getTitle().equals(title);
        if(!isOriginal && updateTitle){
            updateBoth(book, bookUri, title);
        }else if(!isOriginal){
            updateImage(book, bookUri);
        }else if(updateTitle){
            updateTitle(book, title);
        }
    }

    private void updateBoth(Book book, Uri bookUri, String title){
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId).child(book.getId());
        ref.child("title").setValue(title)
                .addOnSuccessListener(aVoid -> {
                    updateImage(book, bookUri);
                })
                .addOnFailureListener(exception ->{
                    presenter.onUpdate(-1);
                });
    }

    private void updateImage(Book book, Uri bookUri){
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId).child(book.getId());
        ref.child("isUsingThumbnail").setValue(0);
        ref.child("thumbnail").setValue(null);
        if(bookUri == null){
            ref.child("imageName").setValue("image_default.PNG")
                    .addOnSuccessListener(aVoid -> {
                        presenter.onUpdate(1);
                    })
                    .addOnFailureListener(exception ->{
                        presenter.onUpdate(-1);
                    });
        }else{
            String imageName = "image_"+book.getId();
            ref.child("imageName").setValue(imageName);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            storage.setMaxUploadRetryTimeMillis(Constants.MAX_UPLOAD_RETRY_MILLIS);

            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("images/"+imageName);
            UploadTask uploadTask = imageRef.putFile(bookUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                presenter.onUpdate(1);
            }).addOnFailureListener(exception -> {
                presenter.onUpdate(-1);
            });
        }
    }

    private void updateTitle(Book book, String title){
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId).child(book.getId());
        ref.child("title").setValue(title)
                .addOnSuccessListener(aVoid -> {
                    presenter.onUpdate(1);
                })
                .addOnFailureListener(exception ->{
                    presenter.onUpdate(-1);
                });
    }

}