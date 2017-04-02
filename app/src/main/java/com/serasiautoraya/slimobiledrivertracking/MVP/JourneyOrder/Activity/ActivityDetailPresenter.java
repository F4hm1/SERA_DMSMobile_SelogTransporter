package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 02/04/2017.
 */

public class ActivityDetailPresenter extends TiPresenter<ActivityDetailView>  {

    @Override
    protected void onAttachView(@NonNull final ActivityDetailView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void onActionClicked(){

    }

}
