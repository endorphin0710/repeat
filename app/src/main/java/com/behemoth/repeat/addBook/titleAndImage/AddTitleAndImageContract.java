package com.behemoth.repeat.addBook.titleAndImage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.behemoth.repeat.model.Book;
import com.google.firebase.storage.StorageReference;

public interface AddTitleAndImageContract {

    interface View{
        Context getContext();
        void showRemoveButton();
        void hideRemoveButton();
        void showSelectedImage(Uri imageUri);
        void showSelectedImage(String imageUrl);
        void showSelectedImage(StorageReference storageReference);
        void GoSearchBookActivity();
        void startActivityForResultFromPresenter(Intent intent, int requestCode);
        void finishActivity();
        void nextStep(Book newBook);
        void onUpdate(int i);
        void showProgressBar();
        void hideProgressBar();
    }

    interface Presenter{
        boolean validateInput(String title);
        Book getBook(String title, Uri bookImage, String thumbnail, int usingThumbnail);
        StorageReference getImageReference(String name);
        void getImageFromGallery();
        void getImageFromCamera();
        void cropGalleryImage(Intent data);
        void cropCameraImage();
        void deleteImage(Uri uri);
        void updateTitleAndImage(Book book, Uri bookUri, String title, boolean isOriginal);
        void onUpdate(int i);
    }

    interface Model{
        StorageReference getImageReference(String name);
        void updateTitleAndImage(Book book, Uri bookUri, String title, boolean isOriginal);
    }

}
