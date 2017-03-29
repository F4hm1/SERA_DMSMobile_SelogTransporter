package com.serasiautoraya.slimobiledrivertracking.MVP.CiCo;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Login.LoginView;
import com.serasiautoraya.slimobiledrivertracking.util.FirebaseInstanceIdServiceUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 29/03/2017.
 */

public class CiCoPresenter extends TiPresenter<CiCoView> {

    @Override
    protected void onAttachView(@NonNull final CiCoView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

}
