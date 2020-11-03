package com.behemoth.repeat.addBook.problem;

import android.net.Uri;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddProblemPresenter implements AddProblemContract.Presenter{

    private static final String TAG = "AddProblemPresenter";

    private AddProblemContract.View view;

    public AddProblemPresenter(AddProblemContract.View view){
        this.view = view;
    }

    @Override
    public void saveBookInfo(Book newBook, ArrayList<Chapter> chapters) {
        String userId = SharedPreference.getInstance().getString(Constants.USER_ID, "");
        newBook.setAuthor(userId);

        long currentTime = System.currentTimeMillis();
        newBook.setCreatedDate(currentTime);

        for(int i = 0; i < chapters.size(); i++){
            Chapter c = chapters.get(i);
            List<Repeat> repeats = new ArrayList<>();
            repeats.add(new Repeat(c.getProblemCount()));
            c.setRepeat(repeats);
        }
        newBook.setChapter(chapters);

        /** firebase **/
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book");
        DatabaseReference bookRef = ref.push();

        String id = bookRef.getKey();
        if(id == null) return;

        newBook.setId(id);

        if(newBook.getImageUri() == null) return;

        Uri file = newBook.getImageUri();
        newBook.setImageUri(null);

        ref.child("book").child(id).setValue(newBook);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.setMaxUploadRetryTimeMillis(Constants.MAX_UPLOAD_RETRY_MILLIS);

        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("images/"+id);
        UploadTask uploadTask = riversRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }
}
