package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.Absence;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class AbsenceRequestHistoryPresenter extends TiPresenter<AbsenceRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    private AbsenceDeleteSendModel mAbsenceDeleteSendModel;

    public AbsenceRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final AbsenceRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData() {
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sAbsenceRequestHistoryList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sAbsenceRequestHistoryList);
        getView().refreshRecyclerView();
    }

    public void onCancelClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        mAbsenceDeleteSendModel = new AbsenceDeleteSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                requestHistoryResponseModel.getId());
        getView().showCancelConfirmationDialog(requestHistoryResponseModel.getRequestDate());
    }

    public void onCancelationSubmitted() {
        getView().toggleLoading(true);
        mRestConnection.deleteData(
                HelperBridge.sModelLoginResponse.getTransactionToken(),
                HelperUrl.DELETE_ABSENCE,
                mAbsenceDeleteSendModel.getHashMapType(),
                new RestCallBackInterfaceModel() {
                    @Override
                    public void callBackOnSuccess(BaseResponseModel response) {
                        getView().toggleLoading(false);
                        getView().showStandardDialog(response.getResponseText(), "Berhasil");
                        getView().refreshAllData();
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
                        getView().showStandardDialog("Gagal membatalkan pengajuan ketidakhadiran, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
                    }
                }
        );
    }

    public void onDetailClicked(RequestHistoryResponseModel requestHistoryResponseModel) {
        getView().showDetailDialog(
                requestHistoryResponseModel.getTransType(),
                requestHistoryResponseModel.getDateStart(),
                requestHistoryResponseModel.getDateEnd(),
                requestHistoryResponseModel.getAbsenceType(),
                "Pengajuan Tanggal " + requestHistoryResponseModel.getRequestDate(),
                requestHistoryResponseModel.getRequestStatus(),
                requestHistoryResponseModel.getApprovalBy()
        );
    }
}
