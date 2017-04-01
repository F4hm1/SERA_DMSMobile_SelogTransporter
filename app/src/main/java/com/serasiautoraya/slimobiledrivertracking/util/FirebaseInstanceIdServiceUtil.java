package com.serasiautoraya.slimobiledrivertracking.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;

/**
 * Created by Randi Dwi Nandra on 13/01/2017.
 */
public class FirebaseInstanceIdServiceUtil extends FirebaseInstanceIdService {

//    SharedPrefsModel mSharedPrefsModel;
//
//    public FirebaseInstanceIdServiceUtil(SharedPrefsModel mSharedPrefsModel) {
//        this.mSharedPrefsModel = mSharedPrefsModel;
//    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG_TOKEN", "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
//        mSharedPrefsModel.apply(HelperKey.KEY_TOKEN_SAVED, token);
    }
}
