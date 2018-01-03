package com.serasiautoraya.slimobiledrivertracking_training.module.CiCo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;

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

    public void onSubmitClicked(String date, String time, String reason, String cicoType) {
        mCicoRequestSendModel = new CiCoRequestSendModel(
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalId()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalId()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalCode()),
                Model.getNonNullable(HelperTransactionCode.WFSTATUS_PENDING),
                Model.getNonNullable(cicoType),
                Model.getNonNullable(date),
                Model.getNonNullable(time),
                Model.getNonNullable(reason),
                Model.getNonNullable(HelperTransactionCode.SUBMIT_TYPE_REQUEST_MOBILE),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalApprovalId()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalApprovalEmail()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalCoordinatorId()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail()),
                Model.getNonNullable(HelperBridge.sModelLoginResponse.getSalesOffice()),
                Model.getNonNullable(HelperTransactionCode.TERMINAL_ID)
        );
        getView().showConfirmationDialog();
    }

    public void onRequestSubmitted() {
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
                getView().showStandardDialog("Gagal melakukan pengajuan cico, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

}
