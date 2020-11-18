package com.behemoth.repeat.addBook.titleAndImage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.behemoth.repeat.R;
import com.behemoth.repeat.addBook.chapter.AddChapterActivity;
import com.behemoth.repeat.model.Book;
import com.behemoth.repeat.util.Util;

public class AddTitleAndImageActivity extends AppCompatActivity implements AddTitleAndImageContract.View, View.OnClickListener, TextWatcher {

    private static final String TAG = "TitleAndImageActivity";
    private AddTitleAndImageContract.Presenter presenter;
    private final int REQUEST_CODE_GALLERY = 0;
    private final int REQUEST_CODE_CAMERA = 1;
    private EditText etTitle;
    private ImageView btnImage;
    private Uri bookImage;
    private ImageView btnRemove;
    private ImageView btnRemoveTitle;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_and_image);

        setToolbar();

        presenter = new AddTitleAndImagePresenter(this);

        btnImage = findViewById(R.id.btnImage);
        btnRemove = findViewById(R.id.btnRemove);
        btnNext = findViewById(R.id.btnNext);
        btnRemoveTitle = findViewById(R.id.btnRemovetitle);

        setTextWatcher();
        setClickEvent();
    }

    private void setTextWatcher(){
        etTitle = findViewById(R.id.etTitle);
        etTitle.addTextChangedListener(this);
    }

    private void setClickEvent(){
        btnImage.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRemoveTitle.setOnClickListener(this);
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
            showChooseOptions();
        }else if(id == R.id.ivRemove){
            removeSelectedImage();
        }else if(id == R.id.btnNext){
            String title = etTitle.getText().toString();

            boolean validated = presenter.validateInput(title);
            if(validated){
                Book book = presenter.getBook(title, bookImage);
                nextStep(book);
            }
        }else if(id == R.id.btnRemovetitle){
            etTitle.setText("");
        }
    }

    private void showChooseOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);

        String[] animals = getResources().getStringArray(R.array.image_options);
        builder.setItems(animals, (dialog, which) -> {
            if(which == 0){
                // 카메라 촬영
            }else{
                // 갤러리에서 가져오기
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , REQUEST_CODE_GALLERY);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CAMERA){

        }else if(requestCode == REQUEST_CODE_GALLERY){
            if (data == null) return;
            Uri imageUri = data.getData();
            startCrop(imageUri);
        }

    }

    private void startCrop(Uri imageUri){
    }

    private void showSelectedImage(Uri imageUri){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnImage.getLayoutParams();
        layoutParams.width = Util.dpToPx(this, 168);
        layoutParams.height = Util.dpToPx(this, 172);
        btnImage.setLayoutParams(layoutParams);
        btnImage.setImageURI(imageUri);
        bookImage = imageUri;
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
    public void Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() > 0){
            showRemoveButton();
        }else{
            hideRemoveButton();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void showRemoveButton(){
        btnRemoveTitle.setVisibility(View.VISIBLE);
    }

    private void hideRemoveButton(){
        btnRemoveTitle.setVisibility(View.GONE);
    }
}