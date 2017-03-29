package com.serasiautoraya.slimobiledrivertracking.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperTransition;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.model.ExpenseObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 13/03/2017.
 */

public class ExpanseCaptureActivity extends AppCompatActivity {

    Bitmap mTempBitmap;
    Uri mUriBitmap;

    @BindView(R.id.expanse_iv_captured) ImageView mIvCaptured;
    @BindView(R.id.expanse_edittext_jumlah) EditText mEditTextJumlah;
    @BindView(R.id.expanse_spinner_tipeexpanse) Spinner mSpinnerTipeExpanse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanse_capture);

        ButterKnife.bind(this);

        initSpinner();

        HelperKey.TransitionType transitionType = (HelperKey.TransitionType) getIntent().getSerializableExtra(HelperKey.KEY_TRANSITION_TYPE);
        String toolbarTitle = getIntent().getExtras().getString(HelperKey.KEY_TITLE_ANIM);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(toolbarTitle);

        HelperTransition.startEnterTransition(transitionType, getWindow(), ExpanseCaptureActivity.this);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ExpanseCaptureActivity.this,
                R.array.expenses_tipe_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTipeExpanse.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }

    @OnClick(R.id.expanse_btn_captureimage)
    void expanseCaptureImageOnClick(View view){
        openGallery();
    }

    @OnClick(R.id.expanse_btn_submit)
    void expanseSubmitOnClick(View view){
        mTempBitmap = null;
        Intent resultIntent = new Intent();

        ExpenseObject expenseObject = new ExpenseObject(mUriBitmap, mEditTextJumlah.getText().toString(), mSpinnerTipeExpanse.getSelectedItem().toString());

//        resultIntent.putExtra("Uri", mUriBitmap);
//        resultIntent.putExtra("Jumlah", mEditTextJumlah.getText().toString());
//        resultIntent.putExtra("Tipe", mSpinnerTipeExpanse.getSelectedItem().toString());

        resultIntent.putExtra("expenseObject", expenseObject);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih bukti expanse"), HelperKey.ACTIVITY_IMAGE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperKey.ACTIVITY_IMAGE_PICKER && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUriBitmap = data.getData();

//                mTempBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUriBitmap);
                mIvCaptured.setImageBitmap(HelperUtil.getThumbnailBitmap(getContentResolver(), mUriBitmap, 512));
//                mIvCaptured.setImageBitmap(mTempBitmap);
                mIvCaptured.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog loading = ProgressDialog.show(ExpanseCaptureActivity.this,
                                "Loading",getResources().getString(R.string.prog_msg_wait),true,false);

                        startProgress(loading);
                    }
                });
        }
    }


    public void startProgress(final ProgressDialog loading) {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mTempBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUriBitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        Thread thread = new Thread(runnable);
//        thread.start();
//        try {
//            thread.join();
//            loading.dismiss();
//            HelperUtil.showImagePreview(mTempBitmap, ExpanseCaptureActivity.this);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new AsyncTask<Void, Integer, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    mTempBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUriBitmap);
                    return "success";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "fail";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if(result.equalsIgnoreCase("success")){
                    Dialog dialog = HelperUtil.showImagePreview(mTempBitmap, ExpanseCaptureActivity.this);
                    loading.dismiss();
                    dialog.show();
                }else{

                }
            }
        }.execute();

    }


}
