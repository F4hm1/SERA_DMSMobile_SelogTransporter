package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRequestPresenter extends TiPresenter<CiCoRequestView> {

    private RestConnection mRestConnection;
    private CiCoRequestSendModel mCicoRequestSendModel;

    @Override
    protected void onAttachView(@NonNull final CiCoRequestView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public CiCoRequestPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void onSubmitClicked(String date, String time, String reason, String cicoType){
        mCicoRequestSendModel = new CiCoRequestSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperBridge.sModelLoginResponse.getPersonalCode(),
                HelperTransactionCode.WFSTATUS_PENDING,
                cicoType,
                date,
                time,
                reason,
                HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE,
                HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                HelperBridge.sModelLoginResponse.getSalesOffice(),
                HelperTransactionCode.TERMINAL_ID
        );
        getView().showConfirmationDialog();
    }

    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_CICO_REQUEST, mCicoRequestSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
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
                getView().showStandardDialog("Gagal melakukan cico, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

}
