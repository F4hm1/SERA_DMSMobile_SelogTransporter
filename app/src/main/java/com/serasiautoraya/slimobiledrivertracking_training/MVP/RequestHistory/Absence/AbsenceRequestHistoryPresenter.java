package com.serasiautoraya.slimobiledrivertracking_training.MVP.RequestHistory.Absence;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.RequestHistory.RequestHistoryResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

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
                HelperUtil.getUserFormDate(requestHistoryResponseModel.getDateStart()),
                HelperUtil.getUserFormDate(requestHistoryResponseModel.getDateEnd()),
                requestHistoryResponseModel.getAbsenceType(),
                "Pengajuan " + HelperUtil.getUserFormDate(requestHistoryResponseModel.getRequestDate()),
                requestHistoryResponseModel.getRequestStatus(),
                requestHistoryResponseModel.getApprovalBy()
        );
    }

}
