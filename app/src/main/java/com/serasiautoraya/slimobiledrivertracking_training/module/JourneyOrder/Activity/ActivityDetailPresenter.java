package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Activity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.ExpensesRequest.ExpenseAvailableSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.ExpensesRequest.ExpenseCheckingResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.DocumentCapture.DocumentCaptureActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim.KlaimActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim.KlaimAvailabilityResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim.KlaimAvailableSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.PodSubmit.PodStatusResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.PodSubmit.PodStatusSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.PodSubmit.PodSubmitActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.StatusUpdateSendModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.LocationModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public void onActionClicked(final Integer assignmentId, final String orderCode, final String statusIsExpense, final String statusIsClaim) {

        if (statusIsExpense.equals("true")){

            final ExpenseAvailableSendModel expenseAvailableSendModel =
                    new ExpenseAvailableSendModel(assignmentId);

            mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_EXPENSE_CHECKING, expenseAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
                @Override
                public void callBackOnSuccess(BaseResponseModel response) {
                    List<ExpenseCheckingResponseModel> expenseCheckingResponseModels = new ArrayList<>();
                    for (int i = 0; i < response.getData().length; i++) {
                        expenseCheckingResponseModels.add(Model.getModelInstance(response.getData()[i], ExpenseCheckingResponseModel.class));
                    }


                    if (expenseCheckingResponseModels.get(0).getCheckingStatus().equals(HelperTransactionCode.TRUE_BINARY)){
                        onCheckClaim();
                    } else {
                        HelperBridge.sTempExpenseAssignmentId = String.valueOf(assignmentId);
                        HelperBridge.sTempSelectedOrderCode = orderCode;
                        getView().setTempFragmentTarget(R.id.nav_expense_request);
                    }


                }

                @Override
                public void callBackOnFail(String response) {
                    /*
                    * TODO change this!
                    * */
                    getView().showToast(response);
                }

                @Override
                public void callBackOnError(VolleyError error) {
                    /*
                    * TODO change this!
                    * */
                    getView().showToast("FAIL: " + error.toString());
                }
            });

        } else {

            onCheckClaim();

                /*final LocationModel locationModel = mRestConnection.getCurrentLocation();
                if (locationModel.getLongitude().equalsIgnoreCase("null")) {
                    getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
                } else {
                    getView().toggleLoading(true);
                    mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                        @Override
                        public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                            String[] timeSplitServer = timeRESTResponseModel.getTime().split(" ");
                            String[] timeSplitActivity = HelperBridge.sAssignedOrderResponseModel.getETD().split(" ");
                            String dateServer = timeSplitServer[0];
                            String dateActivity = timeSplitActivity[0];
                            if(HelperUtil.isDateBeforeOrEqual(HelperUtil.getUserFormDate(dateServer), HelperUtil.getUserFormDate(dateActivity))){
                                onActionDateValid();
                            }else{
                                getView().showStandardDialog("Anda hanya bisa memulai perjalanan pada hari keberangkatan", "Perhatian");
                            }
                            getView().toggleLoading(false);
                        }

                        @Override
                        public void callBackOnFail(String message) {
                            getView().toggleLoading(false);
                            getView().showStandardDialog(message, "Perhatian");
                        }
                    });
                }*/


        }

//        onActionDateValid();
    }

    private void onCheckClaim(){
        try{

            if (HelperBridge.sActivityDetailResponseModel.getIsClaim().equalsIgnoreCase("true"))  {

                final KlaimAvailableSendModel klaimAvailableSendModel =
                        new KlaimAvailableSendModel(HelperBridge.sAssignedOrderResponseModel.AssignmentId + "");

                mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_CLAIM_CHECK, klaimAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
                    @Override
                    public void callBackOnSuccess(BaseResponseModel response) {
                        List<KlaimAvailabilityResponseModel> klaimAvailabilityResponseModels = new ArrayList<>();
                        for (int i = 0; i < response.getData().length; i++) {
                            klaimAvailabilityResponseModels.add(Model.getModelInstance(response.getData()[i], KlaimAvailabilityResponseModel.class));
                        }

                        if (klaimAvailabilityResponseModels.get(0).getClaimAvailability().equals(HelperTransactionCode.TRUE_BINARY)){
                            serverTimeChecking();
                        } else {
                            getView().showConfirmationAlertDialog("Perhatian", "Apakah anda mengetahui potensi kerusakan pada " + HelperBridge.sAssignedOrderResponseModel.getOrderID() + " ?", HelperBridge.sActivityDetailResponseModel.getActivityName());
                        }

                    }

                    @Override
                    public void callBackOnFail(String response) {
                        getView().showToast(response);
                    }

                    @Override
                    public void callBackOnError(VolleyError error) {
                        getView().showToast("FAIL: " + error.toString());
                    }
                });

            } else {
                serverTimeChecking();
            }
        }catch (Exception e){
            Log.e("NUll on CLaim", e + "");
        }

    }

    public void serverTimeChecking(){
        final LocationModel locationModel = mRestConnection.getCurrentLocation();
        if (locationModel.getLongitude().equalsIgnoreCase("null")) {
            getView().showToast("Aplikasi sedang mengambil lokasi (pastikan gps dan peket data tersedia), harap tunggu beberapa saat kemudian silahkan coba kembali.");
        } else {
            getView().toggleLoading(true);
            mRestConnection.getServerTime(new TimeRestCallBackInterface() {
                @Override
                public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                    String[] timeSplitServer = timeRESTResponseModel.getTime().split(" ");
                    String[] timeSplitActivity = HelperBridge.sAssignedOrderResponseModel.getETD().split(" ");
                    String dateServer = timeSplitServer[0];
                    String dateActivity = timeSplitActivity[0];
                    if(HelperUtil.isDateBeforeOrEqual(HelperUtil.getUserFormDate(dateServer), HelperUtil.getUserFormDate(dateActivity))){
                        onActionDateValid();
                    }else{
                        getView().showStandardDialog("Anda hanya bisa memulai perjalanan pada hari keberangkatan", "Perhatian");
                    }
                    getView().toggleLoading(false);
                }

                @Override
                public void callBackOnFail(String message) {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(message, "Perhatian");
                }
            });
        }
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

                        // TODO: Modify HelperRefreshCode Here
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
                    } else if (HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_INSERTED_BLANK)) {
                        HelperBridge.sPodStatusResponseModel = null;
                        activityDetailView.changeActivity(PodSubmitActivity.class);
                    } else if (HelperBridge.sPodStatusResponseModel.getStatusCode().equalsIgnoreCase(HelperTransactionCode.POD_APPROVED)) {
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

        String dateTimeTargetUI = HelperBridge.sActivityDetailResponseModel.getTimeTarget();
        if (dateTimeTarget.length > 2) {
            dateTimeTargetUI = HelperUtil.getUserFormDate(dateTimeTarget[0]) + ", " + dateTimeTarget[1] + " " + dateTimeTarget[2];
        }


        String[] dateTimeBaseLine = RestConnection.getCustomTimeZoneTimeStamp(
                HelperBridge.sActivityDetailResponseModel.getTimeBaseline(),
                "GMT+0" + HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ":00",
                HelperBridge.sActivityDetailResponseModel.getTimezoneTimeTarget() + ""
        ).split(" ");

        String dateTimeBaselineUI = HelperBridge.sActivityDetailResponseModel.getTimeBaseline();
        if (dateTimeBaseLine.length > 2) {
            dateTimeBaselineUI = HelperUtil.getUserFormDate(dateTimeBaseLine[0]) + ", " + dateTimeBaseLine[1] + " " + dateTimeBaseLine[2];
        }


        String[] dateNextActivityTimeTarget = RestConnection.getCustomTimeZoneTimeStamp(
                HelperBridge.sActivityDetailResponseModel.getNextActivityTimeTarget(),
                "GMT+0" + HelperBridge.sActivityDetailResponseModel.getTimezoneNextActivityTimeTarget() + ":00",
                HelperBridge.sActivityDetailResponseModel.getTimezoneNextActivityTimeTarget() + ""
        ).split(" ");

        String nextActivityUI = HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": " + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText();

        if (dateNextActivityTimeTarget.length > 2) {
            nextActivityUI = HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": "
                    + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText()
                    + "(pada " + HelperUtil.getUserFormDate(dateNextActivityTimeTarget[0])
                    + ", " + dateNextActivityTimeTarget[1] + " " + dateNextActivityTimeTarget[2] + ")";
        }



        getView().setDetailData(
                "Order " + HelperBridge.sTempSelectedOrderCode,
                HelperBridge.sTempSelectedOrderCode,
                HelperBridge.sActivityDetailResponseModel.getActivityName(),
                HelperBridge.sActivityDetailResponseModel.getActivityType(),
                HelperBridge.sAssignedOrderResponseModel.getOrigin(),
                this.getSeparatedDestination(HelperBridge.sAssignedOrderResponseModel.getDestination()),
                dateEtd.length > 1 ? HelperUtil.getUserFormDate(dateEtd[0]) + ", " + dateEtd[1] : HelperBridge.sAssignedOrderResponseModel.getETD(),
//                HelperBridge.sAssignedOrderResponseModel.getETD(),
                dateEta.length > 1 ? HelperUtil.getUserFormDate(dateEta[0]) + ", " + dateEta[1] : HelperBridge.sAssignedOrderResponseModel.getETA(),
//                HelperBridge.sAssignedOrderResponseModel.getETA(),
                HelperBridge.sAssignedOrderResponseModel.getCustomer(),
                HelperBridge.sActivityDetailResponseModel.getLocationTargetText(),
                dateTimeTargetUI.split("-")[0].equalsIgnoreCase("1900") ? "-" : dateTimeTargetUI,
//                HelperBridge.sActivityDetailResponseModel.getTimeTarget(),
                dateTimeBaselineUI.split("-")[0].equalsIgnoreCase("1900") ? "-" : dateTimeBaselineUI,
//                HelperBridge.sActivityDetailResponseModel.getTimeBaseline(),
                "-",
                HelperBridge.sActivityDetailResponseModel.getAssignmentId() + "",
                cargoType,
                HelperBridge.sActivityDetailResponseModel.getUnitModel(),
                HelperBridge.sActivityDetailResponseModel.getUnitNumber(),
                docNeed,
                nextActivityUI,
                HelperBridge.sActivityDetailResponseModel.getNextActivityLocationAddress(),
                HelperBridge.sActivityDetailResponseModel.getLocationTargetAddress(),
                HelperBridge.sAssignedOrderResponseModel.getNotes(),
                HelperBridge.sAssignedOrderResponseModel.getCargoDescription()
//                HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": " + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText()
//                HelperBridge.sActivityDetailResponseModel.getNextActivityName() + ": " + HelperBridge.sActivityDetailResponseModel.getNextActivityLocationText() + "(pada "+HelperUtil.getUserFormDate(dateNextActivityTimeTarget[0]) + ", " + dateNextActivityTimeTarget[1]+" "+dateNextActivityTimeTarget[2]+")"
        );

//        Log.d("ACTIVITY DET:", HelperBridge.sActivityDetailResponseModel.getHashMapType().toString());

        /*
        * TODO uncomment this (include activate cannot start journey if not today)
        * */
//        getView().setButtonColor(HelperBridge.sActivityDetailResponseModel.getButtonActivityColor());
        getView().setButtonColor("#1976D2");
        getView().setButtonText(HelperBridge.sActivityDetailResponseModel.getActivityName());
        if(HelperBridge.isClickedFromPlanOrder){
            if (
                    (HelperBridge.sActivityDetailResponseModel.getJourneyActivityId() != HelperTransactionCode.ACTIVITY_ID_FORBIDDEN_UPDATE) &&
                            (HelperBridge.sActiveOrdersList.size() < 1) &&
                            (HelperBridge.sPlanOrderPositionClicked == 0)
                    ) {
                getView().toggleButtonAction(true);
            } else {
                getView().toggleButtonAction(false);
            }
        }else{
            getView().toggleButtonAction(true);
        }
    }

    private String[] getSeparatedDestination(String destination) {
        if(destination.equalsIgnoreCase("")){
            String[] resZero = new String[1];
            resZero[0] = "-";
            return resZero;
        }else{
            return destination.split(HelperKey.SEPARATOR_PIPE);
        }
    }

    public void onRequestSubmitActivity() {
        getView().toggleLoading(true);
        Log.e("KLAIMLOG", mStatusUpdateSendModel.getHashMapType().toString());
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
