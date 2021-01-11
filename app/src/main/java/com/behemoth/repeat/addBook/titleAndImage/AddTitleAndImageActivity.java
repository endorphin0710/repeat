package com.behemoth.repeat.addBook.titleAndImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.SearchBook.SearchBookActivity;
import com.behemoth.repeat.addBook.chapter.AddChapterActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.Util;
import com.bumptech.glide.Glide;

public class AddTitleAndImageActivity extends AppCompatActivity implements AddTitleAndImageContract.View, View.OnClickListener, TextWatcher {

    private AddTitleAndImageContract.Presenter presenter;
    private EditText etTitle;
    private ImageView btnImage;
    private Uri bookImage;
    private String bookThumbnail;
    private int usingThumbnail;
    private ImageView btnRemove;
    private ImageView btnRemoveTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_and_image);

        setToolbar();

        presenter = new AddTitleAndImagePresenter(this);

        setTextWatcher();
        setOnClickListener();
    }

    private void setTextWatcher(){
        etTitle = findViewById(R.id.etTitle);
        etTitle.addTextChangedListener(this);
    }

    private void setOnClickListener(){
        btnImage = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(this);

        btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        btnRemoveTitle = findViewById(R.id.btnRemovetitle);
        btnRemoveTitle.setOnClickListener(this);

        TextView tvSearchBook = findViewById(R.id.searchBook);
        tvSearchBook.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnImage){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                showChooseOptions();
            } else {
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, Constants.PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        }else if(id == R.id.ivRemove){
            removeSelectedImage();
        }else if(id == R.id.btnNext){
            String title = etTitle.getText().toString();
            boolean validated = presenter.validateInput(title);
            if(validated){
                Book book = presenter.getBook(title, bookImage, bookThumbnail, usingThumbnail);
                nextStep(book);
            }
        }else if(id == R.id.btnRemovetitle){
            etTitle.setText("");
        }else if(id == R.id.searchBook){
            GoSearchBookActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_WRITE_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showChooseOptions();
                }
                break;
            case Constants.PERMISSION_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.getImageFromCamera();
                }
                break;
        }
    }

    private void showChooseOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert);
        String[] items = getResources().getStringArray(R.array.image_options);
        builder.setItems(items, (dialog, which) -> {
            if(which == 0){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    presenter.getImageFromCamera();
                } else {
                    requestPermissions(new String[] { Manifest.permission.CAMERA }, Constants.PERMISSION_CAMERA);
                }
            }else{
                presenter.getImageFromGallery();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.REQUEST_CODE_CAMERA && resultCode == RESULT_OK){
            presenter.cropCameraImage();
        }else if(requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            presenter.cropGalleryImage(data);
        }else if(requestCode == Constants.REQUEST_CROP_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                String strUri = data.getStringExtra(Constants.LABEL_CROPPED_IMAGE_URI);
                this.bookImage = Uri.parse(strUri);
                showSelectedImage(bookImage);
                this.usingThumbnail = 0;
            }
        }else if(requestCode == Constants.REQUEST_CODE_SEARCH && resultCode == RESULT_OK){
            if(data != null){
                String title = data.getStringExtra(Constants.LABEL_SEARCHED_TITLE);
                etTitle.setText(title);
                String thumbnail = data.getStringExtra(Constants.LABEL_SEARCHED_THUMBNAIL);
                showSelectedImage(thumbnail);
                this.bookThumbnail = thumbnail;
                this.usingThumbnail = 1;
            }
        }

    }

    private void showSelectedImage(Uri imageUri){
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnImage.getLayoutParams();
//        layoutParams.width = Util.dpToPx(this, 168);
//        layoutParams.height = Util.dpToPx(this, 172);
//        btnImage.setLayoutParams(layoutParams);
        Glide.with(this)
                .load(imageUri)
                .into(btnImage);
        btnRemove.setVisibility(View.VISIBLE);
    }

    private void showSelectedImage(String imageUrl){
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnImage.getLayoutParams();
//        layoutParams.width = Util.dpToPx(this, 168);
//        layoutParams.height = Util.dpToPx(this, 172);
//        btnImage.setLayoutParams(layoutParams);

        Glide.with(this).clear(btnImage);
        if(imageUrl.length() > 0){
            Glide.with(this)
                    .load(imageUrl)
                    .into(btnImage);
        }else{
            Glide.with(this)
                    .load(ContextCompat.getDrawable(this, R.drawable.ic_default))
                    .into(btnImage);
        }
        btnRemove.setVisibility(View.VISIBLE);
    }

    private void removeSelectedImage(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnImage.getLayoutParams();
        layoutParams.width = Util.dpToPx(this, 46);
        layoutParams.height = Util.dpToPx(this, 46);
        btnImage.setLayoutParams(layoutParams);
        btnImage.setImageResource(R.drawable.ic_camera);
        bookImage = null;
        btnRemove.setVisibility(View.GONE);
    }

    private void nextStep(Book newBook){
        Intent i = new Intent(AddTitleAndImageActivity.this, AddChapterActivity.class);
        i.putExtra("book", newBook);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startActivityForResultFromPresenter(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() > 0){
            showRemoveButton();
        }else{
            hideRemoveButton();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    private void showRemoveButton(){
        btnRemoveTitle.setVisibility(View.VISIBLE);
    }

    private void hideRemoveButton(){
        btnRemoveTitle.setVisibility(View.GONE);
    }

    private void GoSearchBookActivity(){
        Intent i = new Intent(AddTitleAndImageActivity.this, SearchBookActivity.class);
        startActivityForResult(i, Constants.REQUEST_CODE_SEARCH, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}