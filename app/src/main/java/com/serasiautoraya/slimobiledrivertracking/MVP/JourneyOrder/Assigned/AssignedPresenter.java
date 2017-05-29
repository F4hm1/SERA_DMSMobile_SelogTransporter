package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class AssignedPresenter extends TiPresenter<AssignedView> {

    private RestConnection mRestConnection;
    private SharedPrefsModel mSharedPrefsModel;
    private boolean isAnyOrderActive;
    private boolean isUpdateLocationActive;

    public AssignedPresenter(RestConnection mRestConnection, SharedPrefsModel mSharedPrefsModel) {
        this.mRestConnection = mRestConnection;
        this.mSharedPrefsModel = mSharedPrefsModel;
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

        getView().toggleLoading(true);
        final AssignedView assignedView = getView();
        AssignedOrderSendModel assignedOrderSendModel = new AssignedOrderSendModel(HelperBridge.sModelLoginResponse.getPersonalId());
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ASSIGNED_ORDER, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<AssignedOrderResponseModel> assignedOrderResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    assignedOrderResponseModels.add(Model.getModelInstance(response.getData()[i], AssignedOrderResponseModel.class));
                    Log.d("ANJIRR", "ss2 : "+assignedOrderResponseModels.get(i).getDestination());
                }
                mergeAssignedOrderData(assignedOrderResponseModels);
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                assignedView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                assignedView.showToast("FAILLLLSSS: " + response);
                assignedView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false));
                assignedView.showToast("FAIL: " + error.toString());
                assignedView.toggleLoading(false);
            }
        });
    }

    private void mergeAssignedOrderData(List<AssignedOrderResponseModel> assignedOrderResponseModels) {
        HelperBridge.sActiveOrdersList = new ArrayList<>();
        HelperBridge.sPlanOutstandingOrdersList= new ArrayList<>();
        isAnyOrderActive = false;

        for (AssignedOrderResponseModel assignedOrderResponseModel :
                assignedOrderResponseModels) {
            Log.d("ASSIGNED_ORDER", assignedOrderResponseModel.getHashMapType().toString());
            if (assignedOrderResponseModel.getActive().equalsIgnoreCase(HelperTransactionCode.ASSIGNED_ORDER_ACTIVE_CODE)){
                HelperBridge.sActiveOrdersList.add(assignedOrderResponseModel);
                isAnyOrderActive = true;
            }else if(assignedOrderResponseModel.getActive().equalsIgnoreCase(HelperTransactionCode.ASSIGNED_ORDER_INACTIVE_CODE)){
                HelperBridge.sPlanOutstandingOrdersList.add(assignedOrderResponseModel);
            }
        }
    }

    public void setUpdateLocationActive(boolean value){
        mSharedPrefsModel.apply(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, value);
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
                    "ETD -"+i,
                    "Active",
                    "Acknowledge");

            AssignedOrderResponseModel planOutlist = new AssignedOrderResponseModel(
                    "OC-XX-"+i,
                    "Status-"+i,
                    "customer -"+i,
                    "Origin -"+i,
                    "Destination -"+i,
                    "ETA -"+i,
                    "ETD -"+i,
                    "Active",
                    "Acknowledge");

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
