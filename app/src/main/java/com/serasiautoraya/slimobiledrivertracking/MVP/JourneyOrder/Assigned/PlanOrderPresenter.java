package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class PlanOrderPresenter extends TiPresenter<PlanOrderView> {

    private SimpleAdapterModel mSimpleAdapterModel;

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    @Override
    protected void onAttachView(@NonNull final PlanOrderView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void loadOrdersdata(){
        mSimpleAdapterModel.setItemList(HelperBridge.sPlanOutstandingOrdersList);
        getView().refreshRecyclerView();
    }

    public void onItemClicked(int position){
                        /*
                * TODO change the way to access id/code order list, code is the title of the list? Pass it to detail driver activity and retrieve data from API based on that.
                * Selection if status is waiting ACK or Waiting to Start
                * Flow : onCreate activity get bundle data/ordercode -> save to local field -> onAttachView call initialize -> initialize call getdata in presenter ->
                * presenter call & get data from API & show progress dialog -> when done, call getview.setDataValue
                * */
        String status = mSimpleAdapterModel.getItem(position).getStatus();

        if(status.equalsIgnoreCase(HelperTransactionCode.WAITING_ACK_CODE)){
            getView().showAcknowledgeDialog();
            getView().showToast("Waiting ACK open dialog");
        }else {
            /*
            * TODO Uncomment this + delete toast
            * */
            getView().showToast("Open activity to start");
//            String orderCode = mSimpleAdapterModel.getItem(position).getTittle();
//            getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, orderCode, ActivityDetailActivity.class);
        }


    }

}
