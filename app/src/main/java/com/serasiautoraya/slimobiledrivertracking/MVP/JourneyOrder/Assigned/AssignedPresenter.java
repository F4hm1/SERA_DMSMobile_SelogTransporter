package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class AssignedPresenter extends TiPresenter<AssignedView> {

    private RestConnection mRestConnection;

    public AssignedPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final AssignedView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void loadOrdersData() {
        HelperBridge.sActiveOrdersList = new ArrayList<>();
        HelperBridge.sPlanOutstandingOrdersList= new ArrayList<>();
        setdatadummmy();

//        getView().toggleLoading(true);
//        AssignedOrderSendModel assignedOrderSendModel = new AssignedOrderSendModel(HelperBridge.sModelLoginResponse.getPersonalId());
//        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ASSIGNED_ORDER, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
//            @Override
//            public void callBackOnSuccess(BaseResponseModel response) {
//                HelperBridge.sModelLoginResponse = Model.getModelInstanceFromString(jsonObject.toString(), LoginResponseModel.class);
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnFail(String response) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAILLLLSSS: " + response);
//                getView().toggleLoading(false);
//            }
//
//            @Override
//            public void callBackOnError(VolleyError error) {
//                /*
//                * TODO change this!
//                * */
//                getView().showToast("FAIL: " + error.toString());
//                getView().toggleLoading(false);
//            }
//        });
    }

    /*
    * TODO delete and change this!
    * */
    private void setdatadummmy(){
        /*
        * TODO retrieve data order (active & outstanding), insert into Helper class, use it from active/plan subfragment
        *
        * */

        for (int i = 0; i < 12; i++) {
            AssignedOrderResponseModel activeList = new AssignedOrderResponseModel(
                    "OC-XX-"+i,
                    "Status-"+i,
                    "customer -"+i,
                    "Origin -"+i,
                    "Destination -"+i,
                    "ETA -"+i,
                    "ETD -"+i);

            AssignedOrderResponseModel planOutlist = new AssignedOrderResponseModel(
                    "OC-XX-"+i,
                    "Status-"+i,
                    "customer -"+i,
                    "Origin -"+i,
                    "Destination -"+i,
                    "ETA -"+i,
                    "ETD -"+i);

            if(i%2 == 0){
                planOutlist.setStatus(HelperTransactionCode.WAITING_ACK_CODE);
            }else {
                planOutlist.setStatus("Status-"+i);
            }

            HelperBridge.sActiveOrdersList.add(activeList);
            HelperBridge.sPlanOutstandingOrdersList.add(planOutlist);
        }
    }

}
