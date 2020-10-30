package com.behemoth.repeat.addBook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Util;

public class TitleAndImageActivity extends AppCompatActivity implements TitleAndImageContract.View, View.OnClickListener {

    private static final String TAG = "TitleAndImageActivity";

    private TitleAndImageContract.Presenter presenter;

    private final int REQUEST_CODE_GALLERY = 0;
    private final int REQUEST_CODE_CAMERA = 1;

    private ImageView btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_and_image);

        setToolbar();

        presenter = new TitleAndImagePresenter(this);

        btnImage = findViewById(R.id.btnImage);

        setClickEvent();
    }

    private void setClickEvent(){
        ImageView addBook = findViewById(R.id.btnImage);
        addBook.setOnClickListener(this);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnImage:
                showChooseOptions();
                break;
            default:
                break;
        }
    }

    private void showChooseOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);

        String[] animals = getResources().getStringArray(R.array.image_options);
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        break;
                    case 1:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , REQUEST_CODE_GALLERY);
                        break;
                    default:
                        break;
                }
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
            Uri selectedImage = data.getData();

            //Display selected photo in image view
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnImage.getLayoutParams();
            layoutParams.width = Util.dpToPx(this, 112);
            layoutParams.height = Util.dpToPx(this, 109);
            btnImage.setLayoutParams(layoutParams);
            btnImage.setImageURI(selectedImage);
        }
    }
}