package com.behemoth.repeat.addBook.titleAndImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.crop.CropActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTitleAndImagePresenter implements AddTitleAndImageContract.Presenter{

    private final AddTitleAndImageContract.View view;
    private final Context viewContext;
    private String currentPhotoPath;

    public AddTitleAndImagePresenter(AddTitleAndImageContract.View view){
        this.view = view;
        this.viewContext = view.getContext();
    }

    @Override
    public boolean validateInput(String title) {
        if(title.isEmpty()){
            Toast.makeText(viewContext, "도서명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public Book getBook(String title, Uri bookImage) {
        Book newBook = new Book();
        newBook.setTitle(title);
        if(bookImage != null){
            newBook.setImageUri(bookImage);
        }
        return newBook;
    }

    @Override
    public void getImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        view.startActivityForResultFromPresenter(pickPhoto, Constants.REQUEST_CODE_GALLERY);
    }

    @Override
    public void getImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(viewContext.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(viewContext, viewContext.getString(R.string.fail_image_creation), Toast.LENGTH_SHORT).show();
                view.finishActivity();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(viewContext, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                view.startActivityForResultFromPresenter(takePictureIntent, Constants.REQUEST_CODE_CAMERA);
            }
        }else{
            Toast.makeText(viewContext, viewContext.getString(R.string.no_camera_handling_activity), Toast.LENGTH_SHORT).show();
            view.finishActivity();
        }
    }

    @Override
    public void cropGalleryImage(Intent data) {
        if (data == null) return;
        Uri imageUri = data.getData();

        Intent i = new Intent(viewContext, CropActivity.class);
        i.putExtra(Constants.LABEL_IMAGE_URI, imageUri.toString());
        view.startActivityForResultFromPresenter(i, Constants.REQUEST_CROP_IMAGE);
    }

    @Override
    public void cropCameraImage() {
        Uri imageUri = FileProvider.getUriForFile(viewContext, "com.example.android.fileprovider", new File(currentPhotoPath));
        try {
            Bitmap imageBitmap = Util.handleSamplingAndRotationBitmap(viewContext, imageUri, currentPhotoPath);
            imageUri = Util.getImageUri(viewContext, imageBitmap);
            Intent i = new Intent(viewContext, CropActivity.class);
            i.putExtra(Constants.LABEL_IMAGE_URI, imageUri.toString());
            view.startActivityForResultFromPresenter(i, Constants.REQUEST_CROP_IMAGE);
        } catch (IOException e) {
            Toast.makeText(viewContext, viewContext.getString(R.string.fail_camera_image_capture), Toast.LENGTH_SHORT).show();
            view.finishActivity();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "REPEAT_" + timeStamp + "_";

        File storageDir = viewContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
