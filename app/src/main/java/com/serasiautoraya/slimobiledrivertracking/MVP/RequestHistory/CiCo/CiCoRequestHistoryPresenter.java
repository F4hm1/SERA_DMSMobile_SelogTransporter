package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.CiCo;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned.ActiveOrderView;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class CiCoRequestHistoryPresenter extends TiPresenter<CiCoRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;

    public CiCoRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final CiCoRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData(){
        mSimpleAdapterModel.setItemList(HelperBridge.sCiCoRequestHistoryList);
        getView().refreshRecyclerView();
    }

}
