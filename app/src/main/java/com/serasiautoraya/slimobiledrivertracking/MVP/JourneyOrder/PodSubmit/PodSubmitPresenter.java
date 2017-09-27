package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.PodSubmit;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.HashMap;

/**
 * Created by Randi Dwi Nandra on 27/09/2017.
 * randi.dwinandra@gmail.com
 */

public class PodSubmitPresenter extends TiPresenter<PodSubmitView>{

    private final RestConnection mRestConnection;

    public PodSubmitPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }


    @Override
    protected void onAttachView(@NonNull final PodSubmitView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
//        getView().setSubmitText(HelperBridge.sActivityDetailResponseModel.getActivityName());
    }


}
