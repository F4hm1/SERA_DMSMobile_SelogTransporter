package com.serasiautoraya.slimobiledrivertracking_training.MVP.RequestHistory;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class RequestHistoryPresenter extends TiPresenter<RequestHistoryView> {

    private RestConnection mRestConnection;

    public RequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final RequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void initialRequestHistoryData(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        Calendar calendarMulai = Calendar.getInstance(TimeZone.getDefault());
        calendarMulai.set(Calendar.DAY_OF_MONTH, 1);
        String startdate = dateFormatter.format(calendarMulai.getTime());

        Calendar calendarAkhir = Calendar.getInstance(TimeZone.getDefault());
        calendarAkhir.set(Calendar.DAY_OF_MONTH, 1);
        calendarAkhir.set(Calendar.MONTH, calendarAkhir.get(Calendar.MONTH) + 1);
        String enddate = dateFormatter.format(calendarAkhir.getTime());

        getView().setTextStartDate(startdate);
        getView().setTextEndDate(enddate);
    }

    public void loadRequestHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        HelperBridge.sAbsenceRequestHistoryList = new ArrayList<>();
        HelperBridge.sCiCoRequestHistoryList = new ArrayList<>();
        HelperBridge.sOvertimeRequestHistoryList = new ArrayList<>();
        HelperBridge.sOLCRequestHistoryList = new ArrayList<>();

        getView().toggleLoadingInitialLoad(true);
        RequestHistorySendModel requestHistorySendModel = new RequestHistorySendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                startDate,
                endDate);

        final RequestHistoryView requestHistoryView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_REQUEST_HISTORY, requestHistorySendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<RequestHistoryResponseModel> requestHistoryResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    requestHistoryResponseModels.add(Model.getModelInstance(response.getData()[i], RequestHistoryResponseModel.class));
                }
                mergeRequestHistoryData(requestHistoryResponseModels);
                requestHistoryView.initializeTabs(
                        HelperBridge.sModelLoginResponse.getReportCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
                );
                requestHistoryView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                requestHistoryView.initializeTabs(
                        HelperBridge.sModelLoginResponse.getReportCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
                );
                requestHistoryView.showToast(response);
                requestHistoryView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                requestHistoryView.initializeTabs(
                        HelperBridge.sModelLoginResponse.getReportCiCo().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportAbsence().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOLCTrip().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY),
                        HelperBridge.sModelLoginResponse.getReportOvertime().equalsIgnoreCase(HelperTransactionCode.TRUE_BINARY)
                );
                requestHistoryView.showToast("ERROR: " + error.toString());
                requestHistoryView.toggleLoadingInitialLoad(false);
            }
        });
    }

    /*
   * TODO delete and change this!
   * */
    private void setdatadummmy() {
        /*
        * TODO retrieve data order (active & outstanding), insert into Helper class, use it from active/plan subfragment
        *
        * */

        String transactTypes[] = {
                HelperTransactionCode.REQUEST_HISTORY_ABSENCE_CODE,
                HelperTransactionCode.REQUEST_HISTORY_CICO_CODE,
                HelperTransactionCode.REQUEST_HISTORY_OLCTRIP_CODE,
                HelperTransactionCode.REQUEST_HISTORY_OVERTIME_CODE
        };

        List<RequestHistoryResponseModel> requestHistoryResponseModels = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            Random r = new Random();
            int randomized = r.nextInt(4 - 0) + 0;

            RequestHistoryResponseModel requestHistoryResponseModel = new RequestHistoryResponseModel(
                    "REQ-CODE" + i,
                    transactTypes[randomized],
                    "Datestart-" + i,
                    "Dateend-" + i,
                    "Timestart-" + i,
                    "Timeend-" + i,
                    "OvertimeType-" + i,
                    "AbsenceType-" + i,
                    "tripCount-" + i,
                    "olcstatus-" + i,
                    i + " - 11 - 2017",
                    "Status - " + i,
                    "approver by - " + i);
            if (i % 3 == 0) {
                requestHistoryResponseModel.setRequestStatus("Disetujui");
            } else {
                requestHistoryResponseModel.setRequestStatus("Menunggu Persetujuan");
            }
            requestHistoryResponseModels.add(requestHistoryResponseModel);
        }
        mergeRequestHistoryData(requestHistoryResponseModels);
    }

    private void mergeRequestHistoryData(List<RequestHistoryResponseModel> requestHistoryResponseModels) {
        HelperBridge.sAbsenceRequestHistoryList = new ArrayList<>();
        HelperBridge.sCiCoRequestHistoryList = new ArrayList<>();
        HelperBridge.sOvertimeRequestHistoryList = new ArrayList<>();
        HelperBridge.sOLCRequestHistoryList = new ArrayList<>();

        for (RequestHistoryResponseModel requestHistory :
                requestHistoryResponseModels) {

            switch (requestHistory.getTransType()) {
                case HelperTransactionCode.REQUEST_HISTORY_ABSENCE_CODE: {
                    HelperBridge.sAbsenceRequestHistoryList.add(requestHistory);
                    break;
                }
                case HelperTransactionCode.REQUEST_HISTORY_CICO_CODE: {
                    HelperBridge.sCiCoRequestHistoryList.add(requestHistory);
                    break;
                }
                case HelperTransactionCode.REQUEST_HISTORY_OVERTIME_CODE: {
                    HelperBridge.sOvertimeRequestHistoryList.add(requestHistory);
                    break;
                }
                case HelperTransactionCode.REQUEST_HISTORY_OLCTRIP_CODE: {
                    HelperBridge.sOLCRequestHistoryList.add(requestHistory);
                    break;
                }
            }

        }
    }

}
