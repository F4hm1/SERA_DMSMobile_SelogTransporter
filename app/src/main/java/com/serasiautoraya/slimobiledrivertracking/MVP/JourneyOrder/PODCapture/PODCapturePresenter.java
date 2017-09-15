package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by randi on 15/08/2017.
 */

public class PODCapturePresenter extends TiPresenter<PODCaptureView> {

    private RestConnection mRestConnection;
    private int mSelectedPhotoId;
    private Bitmap[] mBitmapPhoto;
    private Uri mImageUri;

    private ArrayList<String> uploadedPhotos = new ArrayList<>();
    private HashMap<String, PODPhotoSendModel> mPodPhotoSendModelHashMap;

    public PODCapturePresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
        mPodPhotoSendModelHashMap = new HashMap<>();
        mBitmapPhoto = new Bitmap[10];
    }

    public void capturePhoto(int id) {
        mSelectedPhotoId = id;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try {
            photo = this.createTemporaryFile("temp_pod_sli", ".jpg");
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
//        mBitmapPhoto[mSelectedPhotoId - 1] = bitmapScaled;
        mPodPhotoSendModelHashMap.put(
                mSelectedPhotoId + "",
                new PODPhotoSendModel(
                        HelperUtil.encodeTobase64(bitmapScaled),
                        "ACTIVITY-1",
                        "CODE-1",
                        HelperBridge.sModelLoginResponse.getPersonalId()
                )
        );
        getView().setImageThumbnail(bitmapScaled, mSelectedPhotoId, true);
    }

    public void onRequestSubmitted() {
        for (Map.Entry<String, PODPhotoSendModel> entry : mPodPhotoSendModelHashMap.entrySet()) {
            String containerId = entry.getKey();
            PODPhotoSendModel value = entry.getValue();
            postPhoto(value, containerId);
        }

    }

    private void postPhoto(PODPhotoSendModel podPhotoSendModel, final String containerId) {
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_POD_PHOTO, podPhotoSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showToast(response.getString("responseText"));
                    uploadedPhotos.add(containerId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().toggleLoading(false);
                getView().showToast(response);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().toggleLoading(false);
                getView().showToast("Gagal melakukan pengajuan ketidakhadiran, silahkan periksa koneksi anda kemudian coba kembali");
            }
        });
    }
}
