package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.OLCTrip;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class OLCTripRequestHistoryPresenter extends TiPresenter<OLCTripRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;

    public OLCTripRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final OLCTripRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData(){
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sOLCRequestHistoryList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sOLCRequestHistoryList);
        getView().refreshRecyclerView();
    }

}
