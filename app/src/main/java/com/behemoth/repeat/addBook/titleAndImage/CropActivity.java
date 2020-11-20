package com.behemoth.repeat.addBook.titleAndImage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.behemoth.repeat.R;
import com.behemoth.repeat.util.Constants;
import com.behemoth.repeat.util.Util;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;

public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    private Uri imageUri;
    private Uri croppedUri;
    private CropImageView mCropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        attachOnClickListener();

        String strImageUri = getIntent().getStringExtra(Constants.LABEL_IMAGE_URI);
        imageUri = Uri.parse(strImageUri);
        loadImage();
    }

    private void attachOnClickListener(){
        TextView btnCrop = findViewById(R.id.btnCrop);
        ImageView btnClose = findViewById(R.id.btnClose);

        btnCrop.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    private void loadImage(){
        mCropView = findViewById(R.id.cropImageView);
        mCropView.load(imageUri)
                .useThumbnail(true)
                .execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnCrop){
            mCropView.crop(imageUri).execute(new CropCallback() {
                @Override
                public void onSuccess(Bitmap cropped) {
                    croppedUri = Util.getImageUri(CropActivity.this, cropped);

                    Intent intent = new Intent();
                    intent.putExtra(Constants.LABEL_CROPPED_IMAGE_URI, croppedUri.toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }else if(id == R.id.btnClose){
            finish();
        }
    }

}