package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.BuildConfig;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.PermissionsHelper;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.DocumentCapture.DocumentCaptureView;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.StatusKlaimSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.LocationModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Fahmi Hakim on 31/05/2018.
 * for SERA
 */

public class KlaimPresenter extends TiPresenter<KlaimView> {


    private RestConnection mRestConnection;
    private PermissionsHelper mPermissionsHelper;
    private StatusKlaimSendModel statusKlaimSendModel;
    private Uri mImageUri;
    private int selectedPhotoId;
    private Bitmap[] mBitmapPhoto;
    private Bitmap mBitmapSignature;

    public KlaimPresenter(RestConnection restConnection, PermissionsHelper mPermissionsHelper) {
        this.mRestConnection = restConnection;
        this.mPermissionsHelper = mPermissionsHelper;
        mBitmapPhoto = new Bitmap[3];
        mBitmapPhoto[0] = null;
        mBitmapPhoto[1] = null;
        mBitmapPhoto[2] = null;
    }


    @Override
    public void attachView(@NonNull final KlaimView view) {
        super.attachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        /*getView().initializeFormContent(
                HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getPODGuide()
        );*/
//        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
        getView().setSubmitText("Klaim Activity");
    }

    public void pickImage(int id) {
        selectedPhotoId = id;
        boolean grantedAccess = mPermissionsHelper.isAllPermissionsGranted();
        if (grantedAccess) {
            getView().showPhotoPickerSourceDialog();
        } else {
            mPermissionsHelper.requestLocationPermission();
        }
    }


    public void capturePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            photo = this.createTemporaryFile("temp_bukti_sli", ".jpg");
            photo.delete();
        } catch (Exception e) {
            getView().showToast("Harap cek memory anda! Tidak bisa melakukan pengambilan gambar!");
            return;
        }

        if (Build.VERSION.SDK_INT >= 24) {
            mImageUri = FileProvider.getUriForFile(
                    getView().returnContext(),
                    BuildConfig.APPLICATION_ID + ".provider",
                    photo);
        } else {
            mImageUri = Uri.fromFile(photo);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        getView().startActivityCapture(intent);
    }

    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getPath() + "/.temp/");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        File appStorageDir = Environment.getExternalStorageDirectory();
        appStorageDir = new File(appStorageDir.getPath() + "/" + com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUrl.DIRECTORY_NAME + "/");
        if (!appStorageDir.exists()) {
            appStorageDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void onClickSubmit(final String docNo, final String desc, final String qty, final String price, final String customer, String[] pODReasonList, String[] pODReasonListValue) {
        Log.d("Activiti", "Photo: " + HelperBridge.sActivityDetailResponseModel.getIsPhoto() + "\n" +
                "POD: " + HelperBridge.sActivityDetailResponseModel.getIsPOD() + "\n" +
                "Verification: " + HelperBridge.sActivityDetailResponseModel.getIsCodeVerification() + "\n" +
                "Signature: " + HelperBridge.sActivityDetailResponseModel.getIsSignature());

        String errText = "";
        boolean resultValidation = true;

        if (HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            resultValidation = false;
            errText = "Harap mengambil minimal 1 foto terkait aktifitas ini";
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    resultValidation = true;
                    errText = "";
                }
            }
        }

        /*if (HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            if (mBitmapSignature == null) {
                resultValidation = false;
                errText = "Harap meminta tanda tangan digital kepada PIC terkait";
            }
        }*/


        if (HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            resultValidation = false;
            errText = "Harap mengambil minimal 1 foto terkait aktifitas ini";
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    resultValidation = true;
                    errText = "";
                }
            }


        }

//        if(HelperBridge.sActivityDetailResponseModel.getIsExpenseForm().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){
//            boolean isExpenseValid = false;
////            if(!verificationCode.equalsIgnoreCase("")){
//            isExpenseValid = true;
////            }
//        }

        if (!resultValidation) {
            getView().showToast(errText);
        } else {
            final KlaimView klaimView = getView();
            final LocationModel locationModel = mRestConnection.getCurrentLocation();
            if (locationModel.getLongitude().equalsIgnoreCase("null")) {
                klaimView.showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            } else {
                klaimView.toggleLoading(true);
                mRestConnection.getServerTime(new TimeRestCallBackInterface() {

                    @Override
                    public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                        String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                        String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                        String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                        String date = timeSplit[0];
                        String time = timeSplit[1];
                        String dateMessage = dateSplit[2];
                        String monthMessage = dateSplit[1];
                        String yearMessage = dateSplit[0];

                        /*OrderCode = orderCode; //sAssignedOrderResponseModel
                        AssignmentId = assignmentId; //sAssignedOrderResponseModel
                        ActivityCode = activityCode; //sActivityDetailResponseModel
                        PersonalId = personalId; //sModelLoginResponse
                        PersonalCode = personalCode; //sModelLoginResponse
                        CompanyCode = companyCode; sModelLoginResponse
                        SalesOfficeCode = salesOfficeCode; // sModelLoginResponse
                        PersonelAreaCode = personelAreaCode; //sModelLoginResponse
                        PersonelAreaSubCode = personelAreaSubCode; //sModelLoginResponse
                        PoolCode = poolCode; //sModelLoginResponse
                        DocumentNo = documentNo;
                        Description = description;
                        Quantity = quantity;
                        Amount = amount;
                        Customer = customer;
                        Location = location; //sActivityDetailResponseModel
                        Photo1 = photo1;
                        Photo2 = photo2;
                        Photo3 = photo3;*/


                        statusKlaimSendModel = new StatusKlaimSendModel(
                                HelperBridge.sTempSelectedOrderCode,
                                HelperBridge.sAssignedOrderResponseModel.getAssignmentId() + "",
                                HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                                HelperBridge.sModelLoginResponse.getPersonalId(),
                                HelperBridge.sModelLoginResponse.getPersonalCode(),
                                docNo,
                                desc,
                                qty,
                                price,
                                customer,
                                HelperBridge.sActivityDetailResponseModel.getCurrentLocationId()+ "",//locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                                mBitmapPhoto[0] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[0]),
                                mBitmapPhoto[1] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[1]),
                                mBitmapPhoto[2] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[2])
                        );
                        klaimView.toggleLoading(false);
                        klaimView.showConfirmationDialog(HelperBridge.sTempOrderId);
                    }

                    @Override
                    public void callBackOnFail(String message) {
                        klaimView.toggleLoading(false);
                        klaimView.showStandardDialog(message, "Perhatian");
                    }
                });
            }
        }
    }


    public void setBitmapCaptured() {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT >= 24) {
            bitmap = HelperUtil.saveScaledBitmapSDK24(getView().returnContext().getContentResolver(), mImageUri, HelperUtil.getFirstImageName());
        } else {
            bitmap = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
        }
        final Bitmap bitmapScaled = bitmap;
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void setBitmapCapturedByGallery(String path) {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(path, HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void setBitmapCapturedByGallerySDK24(Uri uri) {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmapSDK24(getView().returnContext().getContentResolver(), uri, HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        getView().startActivityOpenGallery(intent);
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
//        Log.d("DOCUMENT_CAPTURE", mStatusUpdateSendModel.getHashMapType().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 1: " + mStatusUpdateSendModel.getPhoto1().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 2: " + mStatusUpdateSendModel.getPhoto2().toString());
//        Log.d("DOCUMENT_CAPTURE", "Photo 3: " + mStatusUpdateSendModel.getPhoto3().toString());
        final KlaimView klaimView = getView();
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_CLAIM, statusKlaimSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    klaimView.toggleLoading(false);
                    klaimView.showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                klaimView.toggleLoading(false);
                klaimView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                klaimView.toggleLoading(false);
                klaimView.showStandardDialog("Gagal melakukan update status, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void finishCurrentDetailActivity() {
        if (HelperBridge.sCurrentDetailActivity != null) {
            HelperBridge.sCurrentDetailActivity.finish();
        }
    }

}
