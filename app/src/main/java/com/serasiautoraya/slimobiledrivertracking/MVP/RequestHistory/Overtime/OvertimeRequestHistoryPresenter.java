package com.serasiautoraya.slimobiledrivertracking.MVP.RequestHistory.Overtime;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 03/04/2017.
 */

public class OvertimeRequestHistoryPresenter extends TiPresenter<OvertimeRequestHistoryView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;

    public OvertimeRequestHistoryPresenter(RestConnection restConnection) {
        this.mRestConnection = restConnection;
    }

    @Override
    protected void onAttachView(@NonNull final OvertimeRequestHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void loadRequestHistoryData(){
        mSimpleAdapterModel.setItemList(HelperBridge.sOvertimeRequestHistoryList);
        getView().refreshRecyclerView();
    }

}
