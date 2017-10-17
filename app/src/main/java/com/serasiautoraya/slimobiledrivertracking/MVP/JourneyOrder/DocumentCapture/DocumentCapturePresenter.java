package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.DocumentCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.LocationModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Randi Dwi Nandra on 18/04/2017.
 */

public class DocumentCapturePresenter extends TiPresenter<DocumentCaptureView> {

    private RestConnection mRestConnection;
    private StatusUpdateSendModel mStatusUpdateSendModel;
    private Uri mImageUri;
    private int selectedPhotoId;
    private Bitmap[] mBitmapPhoto;
    private Bitmap mBitmapSignature;

    public DocumentCapturePresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
        mBitmapPhoto = new Bitmap[3];
        mBitmapPhoto[0] = null;
        mBitmapPhoto[1] = null;
        mBitmapPhoto[2] = null;
    }

    @Override
    protected void onAttachView(@NonNull final DocumentCaptureView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        getView().initializeFormContent(
                HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                HelperBridge.sActivityDetailResponseModel.getPODGuide()
        );
        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
    }

    public void capturePhoto(int id) {
        selectedPhotoId = id;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            photo = this.createTemporaryFile("temp_bukti_sli", ".jpg");
            photo.delete();
        } catch (Exception e) {
            getView().showToast("Harap cek memory anda! Tidak bisa melakukan pengambilan gambar!");
            return;
        }

        mImageUri = Uri.fromFile(photo);
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
        appStorageDir = new File(appStorageDir.getPath() + "/" + com.serasiautoraya.slimobiledrivertracking.helper.HelperUrl.DIRECTORY_NAME + "/");
        if (!appStorageDir.exists()) {
            appStorageDir.mkdirs();
        }

        return File.createTempFile(part, ext, tempDir);
    }

    public void setBitmapCaptured() {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId, HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY));
    }

    public void captureSignature() {
        getView().startActivitySignature();
    }

    public void setSignCaptured() {
        if (HelperBridge.sBitmapSignature != null) {
            mBitmapSignature = HelperBridge.sBitmapSignature;
            getView().setImageSign(mBitmapSignature);
        }
    }

    public void onClickSubmit(String verificationCode, final String pODReason, final String exFuel, final String exTollPark, final String exEscort, final String exASDP, final String exPortal, final String exBMSPSI, String[] pODReasonList, String[] pODReasonListValue) {
        Log.d("Activiti", "Photo: " + HelperBridge.sActivityDetailResponseModel.getIsPhoto() + "\n" +
                "POD: " + HelperBridge.sActivityDetailResponseModel.getIsPOD() + "\n" +
                "Verification: " + HelperBridge.sActivityDetailResponseModel.getIsCodeVerification() + "\n" +
                "Signature: " + HelperBridge.sActivityDetailResponseModel.getIsSignature());

        String errText = "";
        boolean resultValidation = true;
        final String verificationCodeSplitted = verificationCode.replace("-", "");

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

        if (HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            if (mBitmapSignature == null) {
                resultValidation = false;
                errText = "Harap meminta tanda tangan digital kepada PIC terkait";
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            if (verificationCodeSplitted.equalsIgnoreCase("")) {
                resultValidation = false;
                errText = "Harap isi kode verifikasi terkait aktifitas ini";
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            resultValidation = false;
            errText = "Harap mengambil minimal 1 foto terkait aktifitas ini";
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    resultValidation = true;
                    errText = "";
                }
            }

            if (!resultValidation) {
                String reason = HelperUtil.getValueStringArrayXML(pODReasonList, pODReasonListValue, pODReason);
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
            final LocationModel locationModel = mRestConnection.getCurrentLocation();
            if (locationModel.getLongitude().equalsIgnoreCase("null")) {
                getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            } else {
                getView().toggleLoading(true);
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

                        mStatusUpdateSendModel = new StatusUpdateSendModel(
                                HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                                HelperBridge.sTempSelectedOrderCode,
                                HelperBridge.sModelLoginResponse.getPersonalId(),
                                locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                                locationModel.getAddress(),
                                mBitmapPhoto[0] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[0]),
                                mBitmapPhoto[1] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[1]),
                                mBitmapPhoto[2] == null ? "" : HelperUtil.encodeTobase64(mBitmapPhoto[2]),
                                verificationCodeSplitted,
                                timeRESTResponseModel.getTime(),
                                mBitmapSignature == null ? "" : HelperUtil.encodeTobase64(mBitmapSignature),
                                pODReason,
                                HelperBridge.sActivityDetailResponseModel.getJourneyId() + ""
                        );
                        getView().toggleLoading(false);
                        getView().showConfirmationDialog(HelperBridge.sActivityDetailResponseModel.getActivityName());
                    }

                    @Override
                    public void callBackOnFail(String message) {
                        getView().toggleLoading(false);
                        getView().showStandardDialog(message, "Perhatian");
                    }
                });
            }
        }
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
        Log.d("DOCUMENT_CAPTURE", mStatusUpdateSendModel.getHashMapType().toString());
        Log.d("DOCUMENT_CAPTURE", "Photo 1: " + mStatusUpdateSendModel.getPhoto1().toString());
        Log.d("DOCUMENT_CAPTURE", "Photo 2: " + mStatusUpdateSendModel.getPhoto2().toString());
        Log.d("DOCUMENT_CAPTURE", "Photo 3: " + mStatusUpdateSendModel.getPhoto3().toString());
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_STATUS_UPDATE, mStatusUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal melakukan update status, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void finishCurrentDetailActivity() {
        if (HelperBridge.sCurrentDetailActivity != null) {
            HelperBridge.sCurrentDetailActivity.finish();
        }
    }

    public void validateData(String verificationCode) {
        if (HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isPhotosValid = false;
            for (int i = 0; i < mBitmapPhoto.length; i++) {
                if (mBitmapPhoto[i] != null) {
                    isPhotosValid = true;
                }
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isSignatureValid = false;
            if (mBitmapSignature != null) {
                isSignatureValid = true;
            }
        }

        if (HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            boolean isVerificationCodeValid = false;
            if (!verificationCode.equalsIgnoreCase("")) {
                isVerificationCodeValid = true;
            }
        }

//        if(HelperBridge.sActivityDetailResponseModel.getIsExpenseForm().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)){
//            boolean isExpenseValid = false;
////            if(!verificationCode.equalsIgnoreCase("")){
//                isExpenseValid = true;
////            }
//        }
    }
}