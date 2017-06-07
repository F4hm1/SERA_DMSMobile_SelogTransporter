package com.serasiautoraya.slimobiledrivertracking.MVP.Overtime;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 03/06/2017.
 */

public class OvertimeRequestPresenter extends TiPresenter<OvertimeRequestView>{

    private RestConnection mRestConnection;
    private OvertimeSendModel mOvertimeSendModel;

    public OvertimeRequestPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final OvertimeRequestView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void initialRequestHistoryData(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        String startdate = dateFormatter.format(new Date());

        int maxRetrieveDays = 7;
        String cutOffDate = dateFormatter.format(new Date(System.currentTimeMillis() - (maxRetrieveDays*24*60*60*1000)));


    }

    public void loadRequestHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);
        

        getView().toggleLoadingInitialLoad(true);
        OvertimeAvailableSendModel overtimeAvailableSendModel = new OvertimeAvailableSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                startDate,
                endDate
        );

        final OvertimeRequestView overtimeRequestView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_OVERTIME_AVAILABLE, overtimeAvailableSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<OvertimeAvailableResponseModel> requestHistoryResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    requestHistoryResponseModels.add(Model.getModelInstance(response.getData()[i], OvertimeAvailableResponseModel.class));
                }

                overtimeRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                overtimeRequestView.showToast("FAILLLLSSS: " + response);
                overtimeRequestView.toggleLoadingInitialLoad(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                overtimeRequestView.showToast("FAIL: " + error.toString());
                overtimeRequestView.toggleLoadingInitialLoad(false);
            }
        });
    }

    public void onRequestSubmitted(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.POST_OVERTIME, mOvertimeSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
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
                getView().showStandardDialog("Gagal melakukan pengajuan lembur, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }


    public void onDateSelected(String date, int i) {

    }
}
