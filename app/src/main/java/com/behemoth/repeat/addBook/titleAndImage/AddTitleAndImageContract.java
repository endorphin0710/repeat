package com.behemoth.repeat.addBook.titleAndImage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.behemoth.repeat.model.Book;

public interface AddTitleAndImageContract {

    interface View{
        Context getContext();
        void startActivityForResultFromPresenter(Intent intent, int requestCode);
        void finishActivity();
    }

    interface Presenter{
        boolean validateInput(String title);
        Book getBook(String title, Uri bookImage, String thumbnail, int usingThumbnail);
        void getImageFromGallery();
        void getImageFromCamera();
        void cropGalleryImage(Intent data);
        void cropCameraImage();
    }

    interface Model{

    }

}
