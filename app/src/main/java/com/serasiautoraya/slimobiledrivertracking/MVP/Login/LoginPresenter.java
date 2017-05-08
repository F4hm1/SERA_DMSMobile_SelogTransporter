package com.serasiautoraya.slimobiledrivertracking.MVP.Login;

import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Dashboard.DashboardActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.PermissionsHelper;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 20/03/2017.
 */

public class LoginPresenter extends TiPresenter<LoginView> {
    private SharedPrefsModel mSharedPrefsModel;
    private RestConnection mRestConnection;
    private PermissionsHelper mPermissionsHelper;
    private TelephonyManager mTelephonyManager;

    public LoginPresenter(SharedPrefsModel sharedPrefsModel, RestConnection mRestConnection, PermissionsHelper permissionsHelper, TelephonyManager mTelephonyManager) {
        this.mSharedPrefsModel = sharedPrefsModel;
        this.mRestConnection = mRestConnection;
        this.mPermissionsHelper = permissionsHelper;
        this.mTelephonyManager = mTelephonyManager;
    }

    public void initializePermissions(){
        mPermissionsHelper.requestLocationPermission();
    }

    public void onLogin(String username, String password){
        if(mPermissionsHelper.isAllPermissionsGranted()){
            if(mPermissionsHelper.isAllPermissionsGranted()){
                getView().startInitializeLocation();
            }
            final String fUsername = username;
            final String fPassword = password;

            String deviceID = "";
            if (Build.VERSION.SDK_INT >= 23){
                deviceID = mTelephonyManager.getDeviceId(0);
            }else {
                deviceID = mTelephonyManager.getDeviceId();
            }

            getView().showToast("DEVICE-ID: "+deviceID);

            String tokenFCM = mSharedPrefsModel.get(HelperKey.KEY_TOKEN_SAVED, "token-not-defined");
            if(tokenFCM.equalsIgnoreCase("token-not-defined")){
                tokenFCM = FirebaseInstanceId.getInstance().getToken();
            }

            LoginSendModel loginSendModel = new LoginSendModel(username, password, tokenFCM, deviceID);
            getView().toggleLoading(true);
            mRestConnection.postData("", HelperUrl.POST_LOGIN, loginSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
                @Override
                public void callBackOnSuccess(JSONObject response) {
                    try {
                        JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0);
                        HelperBridge.sModelLoginResponse = Model.getModelInstanceFromString(jsonObject.toString(), LoginResponseModel.class);
                        mSharedPrefsModel.apply(HelperKey.HAS_LOGIN, true);
                        mSharedPrefsModel.apply(HelperKey.KEY_USERNAME, fUsername);
                        mSharedPrefsModel.apply(HelperKey.KEY_PASSWORD, fPassword);
                        getView().toggleLoading(false);
                        getView().changeActivity(DashboardActivity.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                    getView().showToast("FAILLLLSSS: "+response);
                    getView().toggleLoading(false);
                }

                @Override
                public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                    getView().showToast("FAIL: "+error.toString());
                    getView().toggleLoading(false);
                }
            });
        }else {
            mPermissionsHelper.requestLocationPermission();
        }
    }

    @Override
    protected void onAttachView(@NonNull final LoginView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
        if(mPermissionsHelper.isAllPermissionsGranted()){
            getView().startInitializeLocation();
        }
        /*
        * TODO Change / uncomment this, and init location and get permissions?
        * */
        Log.d("TAG_TOKEN", "firebase instance util :"+FirebaseInstanceId.getInstance().getToken());
//        FirebaseInstanceIdServiceUtil firebaseInstanceIdServiceUtil = new FirebaseInstanceIdServiceUtil();
//        firebaseInstanceIdServiceUtil.onTokenRefresh();
        if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
            String password = mSharedPrefsModel.get(HelperKey.KEY_PASSWORD, "");
            String username = mSharedPrefsModel.get(HelperKey.KEY_USERNAME, "");
            /*
            * TODO change foreground of login view to full white or other solid color
            * */
            onLogin(username, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
