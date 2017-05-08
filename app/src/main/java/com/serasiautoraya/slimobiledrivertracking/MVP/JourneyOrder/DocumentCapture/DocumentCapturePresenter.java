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
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
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
        return File.createTempFile(part, ext, tempDir);
    }

    public void setBitmapCaptured() {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
        mBitmapPhoto[selectedPhotoId - 1] = bitmapScaled;
        getView().setImageThumbnail(bitmapScaled, selectedPhotoId);
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

    public void onClickSubmit(String verificationCode, String exFuel, String exTollPark, String exEscort, String exASDP, String exPortal, String exBMSPSI) {

        /*
        * TODO Change this params
        * */

        String verificationCodeSplitted = verificationCode.replace("-", "");
        Log.d("TAG_IMAGES_UPLOADED", "poloa: "+HelperUtil.encodeTobase64(mBitmapPhoto[0]));
        LocationModel locationModel = mRestConnection.getCurrentLocation();

        if(locationModel.getLongitude().equalsIgnoreCase("null")){
            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
        }else{
            mStatusUpdateSendModel = new StatusUpdateSendModel(
                    "Activity Code - EX000867",
                    HelperBridge.sModelLoginResponse.getPersonalId(),
                    locationModel.getLongitude()+", "+locationModel.getLatitude(),
                    locationModel.getAddress(),
                    mBitmapPhoto[0] == null?"":HelperUtil.encodeTobase64(mBitmapPhoto[0]),
                    mBitmapPhoto[1] == null?"":HelperUtil.encodeTobase64(mBitmapPhoto[1]),
                    mBitmapPhoto[2] == null?"":HelperUtil.encodeTobase64(mBitmapPhoto[2]),
                    verificationCodeSplitted,
                    mBitmapSignature == null?"":HelperUtil.encodeTobase64(mBitmapSignature),
                    exFuel,
                    exTollPark,
                    exEscort,
                    exASDP,
                    exPortal,
                    exBMSPSI
            );
            getView().showConfirmationDialog("Activity Name");
        }
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_STATUS_UPDATE, mStatusUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
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
                getView().showStandardDialog("Gagal melakukan penggantian kata sandi, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }
}