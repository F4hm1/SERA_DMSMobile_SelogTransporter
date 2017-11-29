package com.serasiautoraya.slimobiledrivertracking_training.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUtil;

import java.io.File;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class ProveActivity extends AppCompatActivity {
    FloatingActionButton mBtnGetSign, mBtnGetFirstImage, mBtnGetSecondImage;
    Button mBtnSubmit;
    ImageView mImageViewTtd, mImageViewFirst, mImageViewSecond;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        getPermission();
        assignView();
    }

    private void getPermission() {
        if(ContextCompat.checkSelfPermission(ProveActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ProveActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    HelperKey.STORAGE_PERMISSION_GRANTED_CODE);
        }
    }

    private void assignView() {
        mBtnGetSign = (FloatingActionButton) findViewById(R.id.btn_prove_sign);
        mBtnGetFirstImage = (FloatingActionButton) findViewById(R.id.btn_prove_firstimage);
        mBtnGetSecondImage = (FloatingActionButton) findViewById(R.id.btn_prove_secondimage);
        mBtnSubmit = (Button) findViewById(R.id.btn_prove_submit);

        mImageViewTtd = (ImageView) findViewById(R.id.prove_ttd_image);
        mImageViewTtd.setBackgroundColor(ContextCompat.getColor(ProveActivity.this, R.color.colorDeepOn));

        mImageViewFirst = (ImageView) findViewById(R.id.prove_first_image);
        mImageViewSecond = (ImageView) findViewById(R.id.prove_second_image);

        file = new File(HelperUrl.SAVE_DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }

        mBtnGetSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToApprovalActivity = new Intent(ProveActivity.this, SignatureActivity.class);
                startActivityForResult(goToApprovalActivity, HelperKey.ACTIVITY_PROVE);
            }
        });

        mBtnGetFirstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCaptureActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(goToCaptureActivity, HelperKey.ACTIVITY_FIRST_IMAGE_CAPTURE);
            }
        });

        mBtnGetSecondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCaptureActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(goToCaptureActivity, HelperKey.ACTIVITY_SECOND_IMAGE_CAPTURE);
            }
        });


        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence textMsg = Html.fromHtml("Apakah anda yakin "+
                        "<b>"+"semua data telah benar "+"</b>"+"?");

                HelperUtil.showConfirmationAlertDialog(textMsg, ProveActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestProve();
                    }
                });
            }
        });
    }

    private void requestProve() {
        Toast.makeText(this, "Data berhasil disubmit", Toast.LENGTH_LONG).show();
    }

    private void assignActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prove);
        String title = getIntent().getStringExtra(HelperKey.EXTRA_KEY_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case HelperKey.ACTIVITY_PROVE:{
                if(HelperBridge.sTtdBitmap != null){
                    Bitmap ttdBitmap = HelperUtil.rotateBitmap(HelperBridge.sTtdBitmap, 90f);
                    mImageViewTtd.setImageBitmap(ttdBitmap);
                    mImageViewTtd.setBackgroundColor(Color.TRANSPARENT);
                    mImageViewTtd.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
                break;
            }
            case HelperKey.ACTIVITY_FIRST_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap srcBmp = (Bitmap) data.getExtras().get("data");
                    mImageViewFirst.setImageBitmap(srcBmp);
                    HelperUtil.saveImageToDirectory(srcBmp, HelperUtil.getFirstImageName());
//                    mImageViewFirst.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
            }
            case HelperKey.ACTIVITY_SECOND_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap srcBmp = (Bitmap) data.getExtras().get("data");
                    mImageViewSecond.setImageBitmap(srcBmp);
                    HelperUtil.saveImageToDirectory(srcBmp, HelperUtil.getSecondImageName());
                }
                break;
            }
        }
    }
}
