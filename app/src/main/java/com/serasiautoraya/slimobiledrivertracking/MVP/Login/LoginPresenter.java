package com.serasiautoraya.slimobiledrivertracking.MVP.Login;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Dashboard.DashboardActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.FirebaseInstanceIdServiceUtil;
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

    public LoginPresenter(SharedPrefsModel sharedPrefsModel, RestConnection mRestConnection) {
        this.mSharedPrefsModel = sharedPrefsModel;
        this.mRestConnection = mRestConnection;
    }


    public void onLogin(String username, String password){
        LoginSendModel loginSendModel = new LoginSendModel(username, password, mSharedPrefsModel.get(HelperKey.KEY_TOKEN_SAVED, "null"));
        getView().toggleLoading(true);
        mRestConnection.postData("", HelperUrl.POST_LOGIN, loginSendModel.getHashMapType(), new RestCallbackInterface() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0);
                    HelperBridge.sModelLoginResponse = Model.getModelInstanceFromString(jsonObject.toString(), LoginResponseModel.class);
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
    }

    @Override
    protected void onAttachView(@NonNull final LoginView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
//        FirebaseInstanceIdServiceUtil firebaseInstanceIdServiceUtil = new FirebaseInstanceIdServiceUtil(mSharedPrefsModel);
//        firebaseInstanceIdServiceUtil.onTokenRefresh();
        if(mSharedPrefsModel.get(HelperKey.HAS_LOGIN, false)){
            String password = mSharedPrefsModel.get(HelperKey.KEY_PASSWORD, "");
            String username = mSharedPrefsModel.get(HelperKey.KEY_USERNAME, "");
            onLogin(username, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
