package com.serasiautoraya.slimobiledrivertracking_training.module.CiCo;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.Fatigue.FatigueActivity;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;

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

    public void onSubmitCiCo() {
        getView().toggleLoading(true);
        mRestConnection.postData(HelperBridge.sModelLoginResponse.getTransactionToken(), mUrlSubmit, mCiCoRealtimeSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    getView().toggleLoading(false);
                    getView().showStandardDialog(response.getString("responseText"), "Berhasil");
                    if (mCiCoRealtimeSendModel.getCicoType().equalsIgnoreCase(HelperTransactionCode.CLOCK_IN_CODE)) {
                        getView().changeActivity(FatigueActivity.class);
                    }
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

    public void onClockIn() {
        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                String date = timeSplit[0];
                String time = timeSplit[1];
                String dateMessage = dateSplit[2];
                String monthMessage = dateSplit[1];
                String yearMessage = dateSplit[0];

                mCiCoRealtimeSendModel = new CiCoRealtimeSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperBridge.sModelLoginResponse.getPersonalCode(),
                        HelperTransactionCode.WFSTATUS_APPROVED,
                        HelperTransactionCode.CLOCK_IN_CODE,
                        date,
                        time,
                        "Realtime Clock In",
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperTransactionCode.SUBMIT_TYPE_ACTUAL_MOBILE,
                        HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                        latitude,
                        longitude,
                        address,
                        HelperBridge.sModelLoginResponse.getSalesOffice(),
                        HelperTransactionCode.IS_MOBILE,
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

    public void onClockOut() {
        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String[] dateSplit = timeSplit[0].split(HelperKey.USER_DATE_FORMAT_SEPARATOR);
                String date = timeSplit[0];
                String time = timeSplit[1];
                String dateMessage = dateSplit[2];
                String monthMessage = dateSplit[1];
                String yearMessage = dateSplit[0];

                mCiCoRealtimeSendModel = new CiCoRealtimeSendModel(
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperBridge.sModelLoginResponse.getPersonalCode(),
                        HelperTransactionCode.WFSTATUS_APPROVED,
                        HelperTransactionCode.CLOCK_OUT_CODE,
                        date,
                        time,
                        "Realtime Clock Out",
                        HelperBridge.sModelLoginResponse.getPersonalId(),
                        HelperTransactionCode.SUBMIT_TYPE_ACTUAL_MOBILE,
                        HelperBridge.sModelLoginResponse.getPersonalApprovalId(),
                        HelperBridge.sModelLoginResponse.getPersonalApprovalEmail(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorId(),
                        HelperBridge.sModelLoginResponse.getPersonalCoordinatorEmail(),
                        latitude,
                        longitude,
                        address,
                        HelperBridge.sModelLoginResponse.getSalesOffice(),
                        HelperTransactionCode.IS_MOBILE,
                        HelperBridge.sModelLoginResponse.getPoolCode()
                );
                mUrlSubmit = HelperUrl.POST_CICO_REALTIME;
                getView().toggleLoading(false);
                getView().showConfirmationDialog("Clock Out", timeZoneId, dateMessage, monthMessage, yearMessage, time);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });
    }


}
