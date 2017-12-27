package com.serasiautoraya.slimobiledrivertracking_training.module.CiCo;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

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
