package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.CiCo.CiCoView;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class AssignedPresenter extends TiPresenter<AssignedView> {

    @Override
    protected void onAttachView(@NonNull final AssignedView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

}
