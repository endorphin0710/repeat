package com.behemoth.repeat.addBook.problem;

import android.net.Uri;

import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.model.Chapter;
import com.behemoth.repeat.model.Repeat;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.SharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddProblemModel implements AddProblemContract.Model{

    private final AddProblemContract.Presenter presenter;

    public AddProblemModel(AddProblemContract.Presenter p){
        this.presenter = p;
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

        // firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("book").child(userId);

        String id = ref.push().getKey();
        if(id == null) return;

        newBook.setId(id);

        String imageId = "image_"+id;
        Uri file = newBook.getImageUri();
        if(file == null){
            imageId = "default_image.PNG";
        }
        newBook.setImageUri(null);

        newBook.setImageName(imageId);

        ref.child(id).setValue(newBook);

        if(imageId.equals("default_image.PNG")) {
            presenter.onUploadSuccess();
            return;
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.setMaxUploadRetryTimeMillis(Constants.MAX_UPLOAD_RETRY_MILLIS);

        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("images/"+imageId);
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(exception -> {

        }).addOnSuccessListener(taskSnapshot -> {
            presenter.onUploadSuccess();
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
        });
    }
}
