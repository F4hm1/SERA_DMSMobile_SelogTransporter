package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.DocumentCapture;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public class DocumentCaptureActivity extends TiActivity<DocumentCapturePresenter, DocumentCaptureView> implements DocumentCaptureView {

    @BindView(R.id.documents_iv_capture1)
    ImageView mEtDocumentsIvCapture1;
    @BindView(R.id.documents_iv_capture2)
    ImageView mEtDocumentsIvCapture2;
    @BindView(R.id.documents_iv_capture3)
    ImageView mEtDocumentsIvCapture3;

    @BindView(R.id.documents_iv_signature)
    ImageView mEtDocumentsIvSignature;

    @BindView(R.id.documents_et_fuel)
    EditText mEtDocumentsFuel;
    @BindView(R.id.documents_et_tollparking)
    EditText mEtDocumentsTollparking;
    @BindView(R.id.documents_et_escort)
    EditText mEtDocumentsEscort;
    @BindView(R.id.documents_et_asdp)
    EditText mEtDocumentsAsdp;
    @BindView(R.id.documents_et_portal)
    EditText mEtDocumentsPortal;
    @BindView(R.id.documents_et_bmspsi)
    EditText mEtDocumentsBmspsi;

    @BindView(R.id.documents_et_verificationcode)
    EditText mEtDocumentsVerificationCode;


    private String mCurrentVerificationCodeVal = "";
    private boolean isFromCameraActivity = false;
    private boolean isFromSigningActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_documents_capture);
        ButterKnife.bind(this);
    }

    @Override
    @OnTextChanged(value = R.id.documents_et_fuel, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextFuelChangeAfter(Editable editable) {

    }

    @Override
    @OnClick(R.id.documents_iv_capture1)
    public void onClickPhotoCapture1(View view) {
        getPresenter().capturePhoto(1);
    }

    @Override
    @OnClick(R.id.documents_iv_capture2)
    public void onClickPhotoCapture2(View view) {
        getPresenter().capturePhoto(2);
    }

    @Override
    @OnClick(R.id.documents_iv_capture3)
    public void onClickPhotoCapture3(View view) {
        getPresenter().capturePhoto(3);
    }

    @Override
    @OnTouch(R.id.documents_iv_signature)
    public boolean onTouchSignatureCapture(ImageView view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
                view.getDrawable().clearColorFilter();
                view.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL: {
                view.getDrawable().clearColorFilter();
                view.invalidate();
                break;
            }
        }
        return false;
    }

    @Override
    @OnClick(R.id.documents_iv_signature)
    public void onClickSignatureCapture(View view) {
        getPresenter().captureSignature();
    }


    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    public void startActivitySignature() {
        Intent intent = new Intent(DocumentCaptureActivity.this, SigningActivity.class);
        startActivityForResult(intent, HelperKey.ACTIVITY_SIGNATURE_CAPTURE);
    }

    @Override
    public void setImageThumbnail(Bitmap bitmap, int targetIvID) {
        final Bitmap scaledBitmap = bitmap;
        ImageView imageView;
        switch (targetIvID){
            case 1:
                imageView = mEtDocumentsIvCapture1;
                break;
            case 2:
                imageView = mEtDocumentsIvCapture2;
                break;
            case 3:
                imageView = mEtDocumentsIvCapture3;
                break;
            default:
                imageView = mEtDocumentsIvCapture1;
                break;
        }

        imageView.setImageBitmap(scaledBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPreviewImage(scaledBitmap);
                return true;
            }
        });
    }

    @Override
    public void setImageSign(Bitmap bitmap) {
        Bitmap ttdBitmap = HelperUtil.rotateBitmap(bitmap, 90f);
        mEtDocumentsIvSignature.setImageBitmap(ttdBitmap);
        mEtDocumentsIvSignature.setBackgroundColor(Color.TRANSPARENT);
        mEtDocumentsIvSignature.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mEtDocumentsIvSignature.setAdjustViewBounds(true);
    }

    @Override
    @OnClick(R.id.documents_btn_submit)
    public void onClickSubmit(View view) {
        if(getValidationForm()){
            getPresenter().onClickSubmit(
                    mEtDocumentsVerificationCode.getText().toString(),
                    mEtDocumentsFuel.getText().toString(),
                    mEtDocumentsTollparking.getText().toString(),
                    mEtDocumentsEscort.getText().toString(),
                    mEtDocumentsAsdp.getText().toString(),
                    mEtDocumentsPortal.getText().toString(),
                    mEtDocumentsBmspsi.getText().toString()
            );
        }
    }

    @Override
    public void showConfirmationDialog(String activityName) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin semua dokumen telah terpenuhi, dan akan melakukan proses "+
                "<b>"+activityName+"</b>"+"?");

        HelperUtil.showConfirmationAlertDialog(textMsg, DocumentCaptureActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity();
            }
        });
    }

    @Override
    public void initialize() {
//        mEtDocumentsFuel.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsFuel));
//        mEtDocumentsTollparking.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsTollparking));
//        mEtDocumentsEscort.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsEscort));
//        mEtDocumentsAsdp.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsAsdp));
//        mEtDocumentsPortal.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsPortal));
//        mEtDocumentsBmspsi.addTextChangedListener(new CurrencyTextWatcher(mEtDocumentsBmspsi));
        mEtDocumentsVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = mEtDocumentsVerificationCode.getText().toString();

                if (!s.toString().equals(mCurrentVerificationCodeVal)) {
                    String separator = "-";
                    mEtDocumentsVerificationCode.removeTextChangedListener(this);
                    InputFilter[] filters = editable.getFilters();
                    editable.setFilters(new InputFilter[]{});

                    String cleanString = editable.toString().replace(separator, "");
                    String resultString = "";
                    char[] splittedChar = cleanString.toCharArray();
                    String[] splittedString = new String[splittedChar.length];

                    for (int i = 0; i < splittedString.length; i++) {
                        if (i != (splittedString.length - 1)) {
                            splittedString[i] = splittedChar[i] + separator;
                        } else {
                            splittedString[i] = splittedChar[i] + "";
                        }
                        resultString += splittedString[i];
                    }

                    mCurrentVerificationCodeVal = resultString;
                    mEtDocumentsVerificationCode.setText(resultString);
                    mEtDocumentsVerificationCode.setSelection(resultString.length());
                    editable.setFilters(filters);
                    mEtDocumentsVerificationCode.addTextChangedListener(this);
                }

            }
        });

    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(DocumentCaptureActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public DocumentCapturePresenter providePresenter() {
        return new DocumentCapturePresenter(new RestConnection(DocumentCaptureActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFromCameraActivity){
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        }else if(isFromSigningActivity){
            getPresenter().setSignCaptured();
            isFromSigningActivity = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case HelperKey.ACTIVITY_SIGNATURE_CAPTURE:{
                isFromSigningActivity = true;
                break;
            }
            case HelperKey.ACTIVITY_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK ) {
                    isFromCameraActivity = true;
                }
                break;
            }
        }
    }

    private void showPreviewImage(Bitmap bitmap){
        View view = getLayoutInflater().inflate( R.layout.dialog_image_preview, null);

        ImageView postImage = (ImageView) view.findViewById(R.id.iv_container);
        postImage.setImageBitmap(bitmap);

        Dialog builder = new Dialog(DocumentCaptureActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);
        builder.show();
    }

    @Override
    public boolean getValidationForm() {

//        Verify verification code
//        Verify photo input minimum 1
//        Verify signature input


        return true;
    }



}
