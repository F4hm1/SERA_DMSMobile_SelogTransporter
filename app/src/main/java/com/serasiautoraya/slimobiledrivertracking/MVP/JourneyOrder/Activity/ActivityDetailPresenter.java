package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.DocumentCapture.DocumentCaptureActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit.PodStatusResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit.PodStatusSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit.PodSubmitActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.LocationModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailPresenter extends TiPresenter<ActivityDetailView> {

    private RestConnection mRestConnection;
    private ActivityDetailResponseModel mActivityDetailResponseModel;
    private StatusUpdateSendModel mStatusUpdateSendModel;

    public ActivityDetailPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final ActivityDetailView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onActionClicked() {
//        final LocationModel locationModel = mRestConnection.getCurrentLocation();
//        if (locationModel.getLongitude().equalsIgnoreCase("null")) {
//            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
//        } else {
//            getView().toggleLoading(true);
//            mRestConnection.getServerTime(new TimeRestCallBackInterface() {
//                @Override
//                public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
//                    String[] timeSplitServer = timeRESTResponseModel.getTime().split(" ");
//                    String[] timeSplitActivity = HelperBridge.sAssignedOrderResponseModel.getETD().split(" ");
//                    String dateServer = timeSplitServer[0];
//                    String dateActivity = timeSplitActivity[0];
//                    if(HelperUtil.isDateBefore(HelperUtil.getUserFormDate(dateServer), HelperUtil.getUserFormDate(dateActivity))){
//                        onActionDateValid();
//                    }else{
//                        getView().showStandardDialog("Anda hanya bisa memulai perjalanan pada hari keberangkatan", "Perhatian");
//                    }
//                    getView().toggleLoading(false);
//                }
//
//                @Override
//                public void callBackOnFail(String message) {
//                    getView().toggleLoading(false);
//                    getView().showStandardDialog(message, "Perhatian");
//                }
//            });
//        }
        onActionDateValid();
    }

    private void onActionDateValid() {
        if (HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)) {
            checkUploadedPOD(HelperBridge.sActivityDetailResponseModel.getJourneyActivityId());
//            getView().changeActivity(PodSubmitActivity.class);
        } else if (
                HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ||
                        HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ||
                        HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
                ) {
            getView().changeActivity(DocumentCaptureActivity.class);
        } else {
            final LocationModel locationModel = mRestConnection.getCurrentLocation();
            if (locationModel.getLongitude().equalsIgnoreCase("null")) {
                getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
            } else {
                getView().toggleLoading(true);
                mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                    @Override
                    public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
//                        String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
//                        String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
//                        String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
//                        String date = timeSplit[0];
//                        String time = timeSplit[1];
//                        String dateMessage = dateSplit[2];
//                        String monthMessage = dateSplit[1];
//                        String yearMessage = dateSplit[0];

                        mStatusUpdateSendModel = new StatusUpdateSendModel(
                                HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() + "",
                                HelperBridge.sTempSelectedOrderCode,
                                HelperBridge.sModelLoginResponse.getPersonalId(),
                                locationModel.getLatitude() + ", " + locationModel.getLongitude(),
                                locationModel.getAddress(),
                                "",
                                "",
                                "",
                                "",
//                                timeRESTResponseModel.getTime(),
                                RestConnection.getUTCTimeStamp(timeRESTResponseModel),
                                "",
                                "",
                                HelperBridge.sActivityDetailResponseModel.getJourneyId() + "",
                                timeRESTResponseModel.getTime()
                        );
                        getView().toggleLoading(false);
                        getView().showConfirmationDialog("Perhatian", HelperBridge.sActivityDetailResponseModel.getActivityName());
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

    private void checkUploadedPOD(Integer journeyActivityId) {
        PodStatusSendModel activityDetailSendModel = new PodStatusSendModel(HelperBridge.sModelLoginResponse.getPersonalId(), journeyActivityId + "");

        getView().toggleLoading(true);
        final ActivityDetailView activityDetailView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_POD_STATUS, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                if (response.getResponse().equalsIgnoreCase(HelperKey.RESPONSE_STATUS_SUCCESS_CODE)) {
                    HelperBridge.sPodStatusResponseModel = Model.getModelInstance(response.getData()[0], PodStatusResponseModel.class);
                    if (HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_WAITING_APPROVAL)) {
                        activityDetailView.showStandardDialog("Status POD Anda masih menunggu persetujuan koordinator (Sdr. " + HelperBridge.sPodStatusResponseModel.getCoordinatorName() + ")", "Perhatian");
                    } else if (HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_REJECTED)) {
                        activityDetailView.showToast("POD Anda sebelumnya ditolak, harap mengirim kembali bukti POD");
                        activityDetailView.changeActivity(PodSubmitActivity.class);
                    } else if(HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_INSERTED_BLANK)){
                        HelperBridge.sPodStatusResponseModel = null;
                        activityDetailView.changeActivity(PodSubmitActivity.class);
                    } else if(HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_APPROVED)){
                        activityDetailView.showToast("POD Anda telah diterima, mohon lihat kembali daftar order");
                    }
                } else {
                    activityDetailView.showToast(response.getResponseText());
                }
                activityDetailView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                activityDetailView.showToast(response);
                activityDetailView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                activityDetailView.showToast("Terjadi Kesalahan: " + error.toString());
                activityDetailView.toggleLoading(false);
            }
        });
    }

    public void loadDetailOrderData(String orderCode) {

        /*
        * TODO change this lines code below
        * */

        setViewDetailData(orderCode);

//        getView().toggleLoading(true);
//        ActivityDetailSendModel activityDetailSendModel = new ActivityDetailSendModel(orderCode, HelperBridge.sModelLoginResponse.getPersonalId());
//        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
//            @Override
//            public void callBackOnSuccess(BaseResponseModel response) {
//                mActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
//                setViewDetailData();
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnFail(String response) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAILLLLSSS: " + response);
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnError(VolleyError error) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAIL: " + error.toString());
//                getView().toggleLoading(false);
//            }
//        });
    }

    private void setViewDetailData(String orderCode) {
        String cargoType = "";
        String[] cargoTypeArr = HelperBridge.sActivityDetailResponseModel.getCargoType();
        for (int i = 0; i < cargoTypeArr.length; i++) {
            cargoType += cargoTypeArr[i];
            if (i != cargoTypeArr.length - 1) {
                cargoType += ", ";
            }
        }

        String docNeed = "";
        docNeed += HelperBridge.sActivityDetailResponseModel.getIsPhoto().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? "Foto aktifitas, " : "";
        docNeed += HelperBridge.sActivityDetailResponseModel.getIsPOD().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? "Foto bukti POD, " : "";
        docNeed += HelperBridge.sActivityDetailResponseModel.getIsCodeVerification().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? "Kode verifikasi, " : "";
        docNeed += HelperBridge.sActivityDetailResponseModel.getIsSignature().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY) ? "Tanda tangan digital" : "";

        String[] dateEtd = HelperBridge.sAssignedOrderResponseModel.getETD().split(" ");
        String[] dateEta = HelperBridge.sAssignedOrderResponseModel.getETA().split(" ");
//        String[] dateTimeTarget = HelperBridge.sActivityDetailResponseModel.getTimeTarget().split(" ");
//        String[] dateTimeBaseLine = HelperBridge.sActivityDetailResponseModel.getTimeBaseline().split(" ");


        String[] dateTimeTarget = RestConnection.getCustomTimeZoneTimeStamp(
                HelperBridge.sActivityDetailResponseModel.getTimeTarget(),
                "GMT+0" + HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ":00",
                HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ""
        ).split(" ");

        String[] dateTimeBaseLine = RestConnection.getCustomTimeZoneTimeStamp(
                HelperBridge.sActivityDetailResponseModel.getTimeBaseline(),
                "GMT+0" + HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ":00",
                HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ""
        ).split(" ");

        String[] dateNextActivityTimeTarget = RestConnection.getCustomTimeZoneTimeStamp(
                HelperBridge.sActivityDetailResponseModel.getNextActivityTimeTarget(),
                "GMT+0" + HelperBridge.sActivityDetailResponseModel.getTimezoneNextActivityTimeTarget() + ":00",
                HelperBridge.sActivityDetailResponseModel.getTimezoneNextActivityTimeTarget() + ""
        ).split(" ");

        getView().setDetailData(
                "Order " + HelperBridge.sTempSelectedOrderCode,
                HelperBridge.sTempSelectedOrderCode,
                HelperBridge.sActivityDetailResponseModel.getActivityName(),
                HelperBridge.sActivityDetailResponseModel.getActivityType(),
                HelperBridge.sAssignedOrderResponseModel.getOrigin(),
                HelperBridge.sAssignedOrderResponseModel.getDestination(),
                HelperUtil.getUserFormDate(dateEtd[0]) + ", " + dateEtd[1],
//                HelperBridge.sAssignedOrderResponseModel.getETD(),
                HelperUtil.getUserFormDate(dateEta[0]) + ", " + dateEta[1],
//                HelperBridge.sAssignedOrderResponseModel.getETA(),
                HelperBridge.sAssignedOrderResponseModel.getCustomer(),
                HelperBridge.sActivityDetailResponseModel.getLocationTargetText(),
                HelperUtil.getUserFormDate(dateTimeTarget[0]) + ", " + dateTimeTarget[1]+" "+dateTimeTarget[2],
//                HelperBridge.sActivityDetailResponseModel.getTimeTarget(),
                HelperUtil.getUserFormDate(dateTimeBaseLine[0]) + ", " + dateTimeBaseLine[1]+" "+dateTimeBaseLine[2],
//                HelperBridge.sActivityDetailResponseModel.getTimeBaseline(),
                "-",
                HelperBridge.sActivityDetailResponseModel.getAssignmentId() + "",
                cargoType,
                HelperBridge.sActivityDetailResponseModel.getUnitModel(),
                HelperBridge.sActivityDetailResponseModel.getUnitNumber(),
                docNeed,
                HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": " + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText()
//                HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": " + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText() + "(pada "+HelperUtil.getUserFormDate(dateNextActivityTimeTarget[0]) + ", " + dateNextActivityTimeTarget[1]+" "+dateNextActivityTimeTarget[2]+")"
        );

//        Log.d("ACTIVITY DET:", HelperBridge.sActivityDetailResponseModel.getHashMapType().toString());

        /*
        * TODO uncomment this
        * */
//        getView().setButtonColor(HelperBridge.sActivityDetailResponseModel.getButtonActivityColor());
        getView().setButtonColor("#1976D2");
        getView().setButtonText(HelperBridge.sActivityDetailResponseModel.getActivityName());
        if (HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() != HelperTransactionCode.ACTIVITY_ID_FORBIDDEN_UPDATE) {
            getView().toggleButtonAction(true);
        } else {
            getView().toggleButtonAction(false);
        }

    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
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

}
