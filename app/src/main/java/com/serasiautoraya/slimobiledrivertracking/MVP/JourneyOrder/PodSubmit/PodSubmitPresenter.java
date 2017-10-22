package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

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
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture.PODPhotoSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PODCapture.PODUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.LocationModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodSubmitPresenter extends TiPresenter<PodSubmitView> {

    private final RestConnection mRestConnection;
    private int mCurrentSelectedPosition;
    private Uri mImageUri;

//    private HashMap<Integer, PODPhotoSendModel> mPodPhotoSendModelHashMap;

    private ArrayList<Integer> uploadedPhotos = new ArrayList<>();
    private int mPodItemCount;

    public PodSubmitPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
//        mPodPhotoSendModelHashMap = new HashMap<>();
    }

    @Override
    protected void onAttachView(@NonNull final PodSubmitView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
        getView().setGuideText(HelperBridge.sActivityDetailResponseModel.getPODGuide());
        if(HelperBridge.sPodStatusResponseModel != null){

        }
    }

    /**
     * Capture Photo section
     */

    public void pickImage(int id){
        mCurrentSelectedPosition = id;
        getView().showPhotoPickerSourceDialog();
    }

    public void capturePhoto() {
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

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        getView().startActivityOpenGallery(intent);
    }

    public void setBitmapCaptured() {
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());
        getView().setImageThumbnail(bitmapScaled, mCurrentSelectedPosition, true, true);
    }

    public void setBitmapCapturedByGallery(String path){
        final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(path, HelperUtil.getFirstImageName());
        getView().setImageThumbnail(bitmapScaled, mCurrentSelectedPosition, true, true);
    }

    /**
     * End Capture Photo Section
     */

    public void onRequestSubmitted(ArrayList<PodItemModel> podItemModels) {
        mPodItemCount = 0;
        if (podItemModels.size() < 2) {
            getView().showPhotosRequiredAlert();
            return;
        }
        for (PodItemModel value : podItemModels) {
            if (value.getBitmap() != null) {
                getView().toggleProgressBar(value.getAdapterIndex(), true);
                mPodItemCount++;
            }
        }

        for (PodItemModel value : podItemModels) {
            if (value.getBitmap() != null) {
                PODPhotoSendModel podPhotoSendModel = new PODPhotoSendModel(
                        HelperUtil.encodeTobase64(value.getBitmap()),
                        HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                        HelperBridge.sTempSelectedOrderCode,
                        HelperBridge.sModelLoginResponse.getPersonalId()
                );
                postPhoto(podPhotoSendModel, value.getAdapterIndex());
            }
        }
    }

    private void postPhoto(PODPhotoSendModel podPhotoSendModel, final Integer position) {
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_POD_PHOTO, podPhotoSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().showToast(response.getString("responseText"));
                    uploadedPhotos.add(position);
                    getView().toggleProgressBar(position, false);
                    Log.d("PODES", "Success: " + response.getString("responseText"));
                    if (uploadedPhotos.size() == mPodItemCount) {
                        onConfirmActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                getView().showToast(response);
                getView().toggleProgressBar(position, false);
                Log.d("PODES", "Fail: " + response);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                getView().showToast("Gagal melakukan pengajuan ketidakhadiran, silahkan periksa koneksi anda kemudian coba kembali");
                getView().toggleProgressBar(position, false);
                Log.d("PODES", "Error: " + error.getMessage());
            }
        });
    }

    public void onConfirmActivity() {
        getView().showConfirmationDialog(HelperBridge.sActivityDetailResponseModel.getActivityName());
    }

    public void onRequestSubmitActivity(final String reason) {
        getView().toggleLoading(true);
        final LocationModel locationModel = mRestConnection.getCurrentLocation();
        if (locationModel.getLongitude().equalsIgnoreCase("null")) {
            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
        } else {
            getView().toggleLoading(true);
            mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                @Override
                public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                    String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                    String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                    String date = timeSplit[0];
                    String time = timeSplit[1];

                    PODUpdateSendModel podUpdateSendModel = new PODUpdateSendModel(
                            HelperBridge.sModelLoginResponse.getPersonalId(),
                            HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                            reason,
//                            date + " " + time,
                            timeRESTResponseModel.getTime(),
                            HelperBridge.sActivityDetailResponseModel.getJourneyId()+"",
                            locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                            locationModel.getAddress(),
                            RestConnection.getUTCTimeStamp(timeRESTResponseModel)
                    );

                    submitPOD(podUpdateSendModel);
                }

                @Override
                public void callBackOnFail(String message) {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(message, "Perhatian");
                }
            });
        }
    }

    private void submitPOD(PODUpdateSendModel podUpdateSendModel) {
        getView().toggleLoading(true);
        final PodSubmitView podCaptureView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_POD, podUpdateSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    podCaptureView.toggleLoading(false);
                    podCaptureView.showConfirmationSuccess(response.getString("responseText"), "Berhasil");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                podCaptureView.toggleLoading(false);
                podCaptureView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                podCaptureView.toggleLoading(false);
                podCaptureView.showStandardDialog("Gagal menyimpan data POD, silahkan periksa koneksi anda kemudian coba kembali atau harap hubungi administrator", "Perhatian");
            }
        });
    }

    public void finishCurrentDetailActivity() {
        if (HelperBridge.sCurrentDetailActivity != null) {
            HelperBridge.sCurrentDetailActivity.finish();
        }
    }
}
