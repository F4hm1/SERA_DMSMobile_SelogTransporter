package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.PermissionsHelper;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.DocumentCapture.DocumentCaptureActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.DocumentCapture.DocumentCapturePresenter;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class KlaimActivity extends TiActivity<KlaimPresenter, KlaimView> implements KlaimView{


    @BindView(R.id.documents_et_docno)
    EditText mEtDocumentsDocNo;
    @BindView(R.id.documents_et_desc)
    EditText mEtDocumentsDesc;
    @BindView(R.id.documents_et_qty)
    EditText mEtDocumentsQty;
    @BindView(R.id.documents_et_price)
    EditText mEtDocumentsPrice;
    @BindView(R.id.documents_et_customer)
    EditText mEtDocumentsCustomer;

    /*@BindView(R.id.documents_iv_capPOD1)
    ImageView mEtDocumentsIvCapturePOD1;
    @BindView(R.id.documents_iv_capPOD2)
    ImageView mEtDocumentsIvCapturePOD2;
    @BindView(R.id.documents_iv_capPOD3)
    ImageView mEtDocumentsIvCapturePOD3;*/


    @BindView(R.id.documents_iv_capture1)
    ImageView mEtDocumentsIvCapture1;
    @BindView(R.id.documents_iv_capture2)
    ImageView mEtDocumentsIvCapture2;
    @BindView(R.id.documents_iv_capture3)
    ImageView mEtDocumentsIvCapture3;

    private boolean isFromCameraActivity = false;
    //private boolean isFromSigningActivity = false;
    private boolean isFromGalleryActivity = false;
    private Uri temporaryUriGallery;

    @BindView(R.id.documents_btn_submit)
    Button mBtnDocumentsSubmit;

    private ProgressDialog mProgressDialog;
    List<EditText> etList = new ArrayList<>();
    private Long curentTotalAmount = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klaim);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFromCameraActivity) {
            getPresenter().setBitmapCaptured();
            isFromCameraActivity = false;
        } else if(isFromGalleryActivity){
            String imagePath = this.getPathFromUriGallery(temporaryUriGallery);
            if(!imagePath.equalsIgnoreCase("")){
                if (Build.VERSION.SDK_INT >= 24) {
                    getPresenter().setBitmapCapturedByGallerySDK24(temporaryUriGallery);
                }else {
                    getPresenter().setBitmapCapturedByGallery(imagePath);
                }
            }
            isFromGalleryActivity = false;
        }
    }

    private String getPathFromUriGallery(Uri uri){
        try{
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }catch (Exception ex){
            showToast("Gagal mengambil foto dari galeri, silahkan coba kembali");
            return "";
        }
    }

    @NonNull
    @Override
    public KlaimPresenter providePresenter() {
        return new KlaimPresenter(
                new RestConnection(KlaimActivity.this),
                PermissionsHelper.getInstance(this, this)
        );
    }

    @Override
    public boolean getValidationForm() {
        return true;
    }

    @Override
    public void initialize() {
        validatePriceEt(mEtDocumentsPrice);
        validatePriceEt(mEtDocumentsQty);
        //mEtDocumentsQty.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10")});
    }


    public void validatePriceEt(EditText et){

        final String[] current = {""};

        /*RxTextView
                    .textChanges(et)
                    .debounce(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<CharSequence>() {
                        @Override
                        public void call(CharSequence charSequence) {
                            curentTotalAmount = 0L;

                                try {
                                    String strIntAble = et.getText().toString().replace(",","");
                                    Long addedAmount = Long.valueOf(strIntAble);
                                    curentTotalAmount += addedAmount;
                                    String yourFormattedString = String.format("%,d", curentTotalAmount/1000);
                                    //setEdittext(yourFormattedString);
                                    //setTotalExpense("Rp." + yourFormattedString);
                                } catch (Exception ex) {
                                    //setEdittext("Angka yang anda masukan salah");
                                }

                        }
                    });*/

            final TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {




                    /*if(!charSequence.toString().equals(current[0])){
                        et.removeTextChangedListener(this);

                        String cleanString = charSequence.toString().replaceAll("[$,.,Rp]", "");

                        double parsed = Double.parseDouble(cleanString);
                        String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                        current[0] = formatted;
                        et.setText(formatted);
                        et.setSelection(formatted.length());
                        et.addTextChangedListener(this);
                    }*/
                }



                @Override
                public void afterTextChanged(Editable editable) {

                    et.removeTextChangedListener(this);

                    try {
                        String originalString = et.getText().toString();

                        Long longval;
                        if (originalString.contains(",")) {
                            originalString = originalString.replaceAll(",", "");
                        }
                        longval = Long.parseLong(originalString);

                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        formatter.applyPattern("#,###,###,###");
                        String formattedString = formatter.format(longval);

                        //setting text after format to EditText
                        et.setText(formattedString);
                        et.setSelection(et.getText().length());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    }

                    et.addTextChangedListener(this);

                  /* et.removeTextChangedListener(this);
                    try {
                        //String strIntAble = et.getText().toString().replace(",", "");
                        String yourFormattedString = doubleToStringNoDecimal(Double.parseDouble(et.getText().toString()));
                        et.setText(yourFormattedString);
                        et.setSelection(et.getText().length());
                    } catch (Exception ex) {
                        Log.d("RXTEXT", ex.getMessage());
                    }
                    et.addTextChangedListener(this);*/

                }
            };

            et.addTextChangedListener(textWatcher);

    }

    public static String doubleToStringNoDecimal(double d) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);;
        formatter .applyPattern("#,###,###,###");
        return formatter.format(d);
    }

    @Override
    public void toggleLoading(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(KlaimActivity.this, getResources().getString(R.string.progress_msg_loaddata), getResources().getString(R.string.prog_msg_wait), true, false);
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(KlaimActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showStandardDialog(String message, String Title) {
        HelperUtil.showSimpleAlertDialogCustomTitle(message, KlaimActivity.this, Title);
    }

    @Override
    @OnClick({R.id.documents_iv_capture1})
    public void onClickPhotoCapture1(View view) {
        getPresenter().pickImage(1);
    }

    @Override
    @OnClick({R.id.documents_iv_capture2})
    public void onClickPhotoCapture2(View view) {
        getPresenter().pickImage(2);
    }

    @Override
    @OnClick({R.id.documents_iv_capture3})
    public void onClickPhotoCapture3(View view) {
        getPresenter().pickImage(3);
    }

    @Override
    public void showConfirmationDialog(String orderCode) {
        CharSequence textMsg = Html.fromHtml("Apakah anda yakin akan mengirimkan data potensi kerusakan pada order " +
                "<b>" + orderCode + "</b>" + "?");

        HelperUtil.showConfirmationAlertDialog(textMsg, KlaimActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().onRequestSubmitActivity();
            }
        });
    }

    @Override
    public void startActivityCapture(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    @Override
    @OnClick(R.id.documents_btn_submit)
    public void onClickSubmit(View view) {
        if (getValidationForm()) {
            getPresenter().onClickSubmit(
                    mEtDocumentsDocNo.getText().toString(),
                    mEtDocumentsDesc.getText().toString(),
                    mEtDocumentsQty.getText().toString(),
                    mEtDocumentsPrice.getText().toString().replace(",",""),
                    mEtDocumentsCustomer.getText().toString(),
                    getResources().getStringArray(R.array.documents_podreason_array),
                    getResources().getStringArray(R.array.documents_podreason_array_val)
            );
        }
    }

    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }


    @Override
    public void initializeFormContent() {

    }

    @Override
    public void setSubmitText(String text) {
        mBtnDocumentsSubmit.setText("KIRIM DATA");
    }

    @Override
    public void showPhotoPickerSourceDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_capture_photopickerselection, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        Button btnCamera = (Button) promptView.findViewById(R.id.capture_dialog_btncamera);
        Button btnGallery = (Button) promptView.findViewById(R.id.capture_dialog_btngallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPresenter().capturePhoto();
                alertD.dismiss();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPresenter().openGallery();
                alertD.dismiss();
            }
        });

        alertD.setView(promptView);
        alertD.show();

    }

    @Override
    public void setImageThumbnail(Bitmap bitmap, int targetIvID, boolean isPOD) {
        final Bitmap scaledBitmap = bitmap;
        ImageView imageView;
        switch (targetIvID) {
            case 1:
                imageView = isPOD ? mEtDocumentsIvCapture1 : mEtDocumentsIvCapture1;
                break;
            case 2:
                imageView = isPOD ? mEtDocumentsIvCapture2 : mEtDocumentsIvCapture2;
                break;
            case 3:
                imageView = isPOD ? mEtDocumentsIvCapture3 : mEtDocumentsIvCapture3;
                break;
            default:
                imageView = isPOD ? mEtDocumentsIvCapture1 : mEtDocumentsIvCapture1;
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

    private void showPreviewImage(Bitmap bitmap) {
        View view = getLayoutInflater().inflate(R.layout.dialog_image_preview, null);

        ImageView postImage = (ImageView) view.findViewById(R.id.iv_container);
        postImage.setImageBitmap(bitmap);

        Dialog builder = new Dialog(KlaimActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);
        builder.show();
    }

    @Override
    public void startActivityOpenGallery(Intent intent) {
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE_GALLERY);
    }

    @Override
    public void showConfirmationSuccess(String message, String title) {
        HelperUtil.showSimpleAlertDialogCustomTitleAction(message, KlaimActivity.this, title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().finishCurrentDetailActivity();
                        finish();
                    }
                },
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getPresenter().finishCurrentDetailActivity();
                        finish();
                    }
                });
    }

    @Override
    public Context returnContext() {
        return this;
    }

    @Override
    public void setEdittext(String content) {
        mEtDocumentsPrice.setText(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /*case HelperKey.ACTIVITY_SIGNATURE_CAPTURE: {
                isFromSigningActivity = true;
                break;
            }*/
            case HelperKey.ACTIVITY_IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    isFromCameraActivity = true;
                }
                break;
            }
            case HelperKey.ACTIVITY_IMAGE_CAPTURE_GALLERY:{
                if (resultCode == RESULT_OK) {
                    isFromGalleryActivity = true;
                    temporaryUriGallery = data.getData();
                }
            }
        }
    }
}
