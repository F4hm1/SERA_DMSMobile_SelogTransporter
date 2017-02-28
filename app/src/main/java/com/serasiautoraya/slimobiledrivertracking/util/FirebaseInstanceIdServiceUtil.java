package com.serasiautoraya.slimobiledrivertracking.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Randi Dwi Nandra on 13/01/2017.
 */
public class FirebaseInstanceIdServiceUtil extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG_TOKEN", "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Send token to dm server

//        SIMEndDate
//                SIMNumber
//        KTPEndDate
//                KTPNumber
//
//
//        1000 Cuti
//        9000 Off
//
//

    }
}
