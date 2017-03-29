package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoRealtimePresenter extends TiPresenter<CiCoRealtimeView> {

    private RestConnection mRestConnection;
    private CiCoRealtimeSendModel mCiCoRealtimeSendModel;
    private String mUrlSubmit;

    public CiCoRealtimePresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void onSubmitCiCo(){
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), mUrlSubmit, mCiCoRealtimeSendModel.getHashMapType(), new RestCallbackInterface() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Perhatian");
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
                * TODO change this!
                * */
                getView().toggleLoading(false);
                getView().showStandardDialog("Gagal melakukan cico, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    public void onClockIn(){
        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String[] dateSplit =  timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                String date = timeSplit[0];
                String time = timeSplit[1];
                String dateMessage = dateSplit[2];
                String monthMessage = dateSplit[1];
                String yearMessage = dateSplit[0];

                mCiCoRealtimeSendModel = new CiCoRealtimeSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperBridge.sModelLoginResponse.getPersonalCode(),
                        "WFSTS_10",
                        HelperKey.CLOCK_IN_CODE,
                        date,
                        time,
                        "",
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        "CCSMT_04",
                        HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                        latitude,
                        longitude,
                        address,
                        HelperBridge.sModelLoginResponse.getSalesOffice(),
                        "1",
                        HelperBridge.sModelLoginResponse.getPoolCode()
                );
                mUrlSubmit = HelperUrl.POST_CICO_REALTIME;
                getView().toggleLoading(false);
                getView().showConfirmationDialog("Clock In", timeZoneId, dateMessage, monthMessage, yearMessage, time);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });
    }

    public void onClockOut(){
        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String[] dateSplit =  timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                String date = timeSplit[0];
                String time = timeSplit[1];
                String dateMessage = dateSplit[2];
                String monthMessage = dateSplit[1];
                String yearMessage = dateSplit[0];

                mCiCoRealtimeSendModel = new CiCoRealtimeSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperBridge.sModelLoginResponse.getPersonalCode(),
                        "WFSTS_10",
                        HelperKey.CLOCK_OUT_CODE,
                        date,
                        time,
                        "",
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        "CCSMT_04",
                        HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                        latitude,
                        longitude,
                        address,
                        HelperBridge.sModelLoginResponse.getSalesOffice(),
                        "1",
                        HelperBridge.sModelLoginResponse.getPoolCode()
                );
                mUrlSubmit = HelperUrl.POST_CICO_REALTIME;
                getView().toggleLoading(false);
                getView().showConfirmationDialog("Clock In", timeZoneId, dateMessage, monthMessage, yearMessage, time);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });
    }

    public static String getTimeZoneID(TimeRESTResponseModel timeRESTResponseModel){
        String result = "";
        switch (timeRESTResponseModel.getGmtOffset()){
            case "7":
                result = "WIB";
                break;
            case "8":
                result = "WITA";
                break;
            case "9":
                result = "WIT";
                break;
        }
        return result;
    }


}
