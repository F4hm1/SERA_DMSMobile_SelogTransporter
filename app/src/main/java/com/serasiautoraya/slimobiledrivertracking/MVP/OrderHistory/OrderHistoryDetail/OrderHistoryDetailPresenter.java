package com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 23/05/2017.
 */

public class OrderHistoryDetailPresenter extends TiPresenter<OrderHistoryDetailView> {

    @Override
    protected void onAttachView(@NonNull final OrderHistoryDetailView view) {
        super.onAttachView(view);
        getView().initialize();
    }

    public void loadActivitiesOrderData(){
        for (int i = 0; i < HelperBridge.sOrderHistoryDetailActivityList.size(); i++) {
            OrderHistoryDetailResponseModel orderHistoryDetailResponseModel = HelperBridge.sOrderHistoryDetailActivityList.get(i);
            getView().addActivityData(
                    orderHistoryDetailResponseModel.getSequence()+ " - " + orderHistoryDetailResponseModel.getActivityName(),
                    orderHistoryDetailResponseModel.getActivityCode(),
                    orderHistoryDetailResponseModel.getActivityType(),
                    orderHistoryDetailResponseModel.getTimeTarget(),
                    orderHistoryDetailResponseModel.getTimeBaseline(),
                    orderHistoryDetailResponseModel.getTimeActual(),
                    orderHistoryDetailResponseModel.getLocationTargetText()
            );
        }
    }

    public void setTempFragmentTarget(int id){
        HelperBridge.sTempFragmentTarget = id;
        Log.d("DASHBOARDSS", "ORHISTO: "+HelperBridge.sTempFragmentTarget);
    }

}
