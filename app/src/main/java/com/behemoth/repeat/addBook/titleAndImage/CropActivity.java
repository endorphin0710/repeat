package com.behemoth.repeat.addBook.titleAndImage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
        ImageView btnCrop = findViewById(R.id.btnCrop);
        ImageView btnRotateLeft = findViewById(R.id.rotate_left);
        ImageView btnRotateRight = findViewById(R.id.rotate_right);
        ImageView btnClose = findViewById(R.id.btnClose);
        TextView ratioFree = findViewById(R.id.ratio_free);
        TextView ratioSquare = findViewById(R.id.ratio_square);
        TextView ratio34 = findViewById(R.id.ratio_34);
        TextView ratio43 = findViewById(R.id.ratio_43);
        TextView ratio169 = findViewById(R.id.ratio_169);
        TextView ratio916 = findViewById(R.id.ratio_916);
        TextView ratio57 = findViewById(R.id.ratio_57);
        TextView ratio75 = findViewById(R.id.ratio_75);

        btnCrop.setOnClickListener(this);
        btnRotateLeft.setOnClickListener(this);
        btnRotateRight.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        ratioFree.setOnClickListener(this);
        ratioSquare.setOnClickListener(this);
        ratio34.setOnClickListener(this);
        ratio43.setOnClickListener(this);
        ratio169.setOnClickListener(this);
        ratio916.setOnClickListener(this);
        ratio57.setOnClickListener(this);
        ratio75.setOnClickListener(this);
    }

    private void setCropConfig(){
        mCropView.setCropMode(CropImageView.CropMode.FREE);
        mCropView.setFrameColor(getColor(R.color.black));
        mCropView.setHandleColor(getColor(R.color.black));
        mCropView.setGuideColor(getColor(R.color.black));
        mCropView.setBackgroundColor(getColor(R.color.colorGradientStart));
        mCropView.setHandleSizeInDp((int) getResources().getDimension(R.dimen.crop_handle_size));
        mCropView.setTouchPaddingInDp(32);
    }

    private void loadImage(){
        mCropView = findViewById(R.id.cropImageView);
        setCropConfig();
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
            Log.d("juntae", "crop!");
            mCropView.crop(imageUri).execute(new CropCallback() {
                @Override
                public void onSuccess(Bitmap cropped) {
                    Log.d("juntae", "success!");
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
        }else if(id == R.id.rotate_right){
            mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
        }else if(id == R.id.rotate_left){
            mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
        }else if(id == R.id.ratio_free){
            mCropView.setCropMode(CropImageView.CropMode.FREE);
        }else if(id == R.id.ratio_square){
            mCropView.setCropMode(CropImageView.CropMode.SQUARE);
        }else if(id == R.id.ratio_34){
            mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
        }else if(id == R.id.ratio_43){
            mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
        }else if(id == R.id.ratio_169){
            mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
        }else if(id == R.id.ratio_916){
            mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
        }else if(id == R.id.ratio_57){
            mCropView.setCustomRatio(5,7);
        }else if(id == R.id.ratio_75){
            mCropView.setCustomRatio(7,5);
        }
    }

}