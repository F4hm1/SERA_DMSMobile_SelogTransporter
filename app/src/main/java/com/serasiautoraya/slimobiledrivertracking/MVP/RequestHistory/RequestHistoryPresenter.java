package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void loadRequestHistoryData(String startDate, String endDate) {
        HelperBridge.sAbsenceRequestHistoryList = new ArrayList<>();
        HelperBridge.sCiCoRequestHistoryList = new ArrayList<>();
        HelperBridge.sOvertimeRequestHistoryList = new ArrayList<>();
        HelperBridge.sOLCRequestHistoryList = new ArrayList<>();
        /*
        * TODO Delete this method calling & uncomment + test data retrieval from API
        * */
        setdatadummmy();

//        getView().toggleLoading(true);
//        RequestHistorySendModel requestHistorySendModel = new RequestHistorySendModel(
//                HelperBridge.sModelLoginResponse.getPersonalId(),
//                startDate,
//                endDate);
//
//        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_REQUEST_HISTORY, requestHistorySendModel.getHashMapType(), new RestCallBackInterfaceModel() {
//            @Override
//            public void callBackOnSuccess(BaseResponseModel response) {
//                List<RequestHistoryResponseModel> requestHistoryResponseModels = new ArrayList<>();
//                for (int i = 0; i < response.getData().length; i++) {
//                    requestHistoryResponseModels.add(Model.getModelInstance(response.getData()[i], RequestHistoryResponseModel.class));
//                }
//                mergeRequestHistoryData(requestHistoryResponseModels);
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
