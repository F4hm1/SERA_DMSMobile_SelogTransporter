package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Assigned;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.SharedPrefsModel;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking_training.module.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking_training.module.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking_training.util.HttpsTrustManager;

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
    private String currentAct = "";

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
        HelperBridge.sPlanOutstandingOrdersList = new ArrayList<>();

        getView().toggleLoading(true);
        final AssignedView assignedView = getView();
        AssignedOrderSendModel assignedOrderSendModel = new AssignedOrderSendModel(
                HelperBridge.sModelLoginResponse.getPersonalId(),
                HelperTransactionCode.ASSIGNED_REQUEST_OPEN,
                "1900-01-01",
                "2100-01-01"
        );
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ASSIGNED_ORDER, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                Log.d("SIT_FMS", "SUCCESS : "+response.getResponseText());
                List<AssignedOrderResponseModel> assignedOrderResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    assignedOrderResponseModels.add(Model.getModelInstance(response.getData()[i], AssignedOrderResponseModel.class));
                    currentAct = assignedOrderResponseModels.get(i).getCurrentActivity();
                    Log.d("ANJIRR", "ss2 : " + assignedOrderResponseModels.get(i).getDestination());
                }
                mergeAssignedOrderData(assignedOrderResponseModels);
                HelperBridge.sListOrderRetrievalSuccess = true;
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false), currentAct);
                assignedView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                HelperBridge.sListOrderRetrievalSuccess = false;
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false),currentAct);
                assignedView.showToast(response);
                assignedView.toggleLoading(false);
                Log.d("SIT_FMS", "FAIL : "+response);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                HelperBridge.sListOrderRetrievalSuccess = false;
                assignedView.initializeTabs(isAnyOrderActive, mSharedPrefsModel.get(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, false),currentAct);
                assignedView.showToast("ERROR: " + error.toString());
                Log.d("SIT_FMS", "ERROR : "+error.toString());
                assignedView.toggleLoading(false);
            }
        });
    }

    private void mergeAssignedOrderData(List<AssignedOrderResponseModel> assignedOrderResponseModels) {
        HelperBridge.sActiveOrdersList = new ArrayList<>();
        HelperBridge.sPlanOutstandingOrdersList = new ArrayList<>();
        isAnyOrderActive = false;
        HelperBridge.isPlanOrderShow = true;
        for (AssignedOrderResponseModel assignedOrderResponseModel :
                assignedOrderResponseModels) {
            Log.d("ASSIGNED_ORDER", assignedOrderResponseModel.getHashMapType().toString());
            if (assignedOrderResponseModel.getStatus() == HelperTransactionCode.ASSIGNED_STATUS_ONJOURNEY_IN) {
                HelperBridge.sActiveOrdersList.add(assignedOrderResponseModel);
                isAnyOrderActive = true;
                HelperBridge.isPlanOrderShow = false;
            } else if (
                    assignedOrderResponseModel.getStatus() == HelperTransactionCode.ASSIGNED_STATUS_ACK_IN
                            || assignedOrderResponseModel.getStatus() == HelperTransactionCode.ASSIGNED_STATUS_NOTACK_IN) {
                HelperBridge.sPlanOutstandingOrdersList.add(assignedOrderResponseModel);
            }
        }
    }

    public void setUpdateLocationActive(boolean value) {
        mSharedPrefsModel.apply(HelperKey.KEY_IS_UPDATE_LOCATION_ACTIVE, value);
    }

    /*
    * TODO delete and change this!
    * */
    private void setdatadummmy() {
        /*
        * TODO retrieve data order (active & outstanding), insert into Helper class, use it from active/plan subfragment
        *
        * */

//        for (int i = 0; i < 12; i++) {
//            AssignedOrderResponseModel activeList = new AssignedOrderResponseModel(
//                    "OC-XX-" + i,
//                    i,
//                    1,
//                    "customer -" + i,
//                    "Origin -" + i,
//                    "Destination -" + i,
//                    "ETA -" + i,
//                    "ETD -" + i);
//
//            AssignedOrderResponseModel planOutlist = new AssignedOrderResponseModel(
//                    "OC-XX-" + i,
//                    i,
//                    2,
//                    "customer -" + i,
//                    "Origin -" + i,
//                    "Destination -" + i,
//                    "ETA -" + i,
//                    "ETD -" + i);
//
//            if (i % 2 == 0) {
//                planOutlist.setStatus(1);
//            } else {
//                if (i % 3 == 0) {
//                    planOutlist.setStatus(HelperTransactionCode.ASSIGNED_STATUS_NOTACK_IN);
//                } else {
//                    planOutlist.setStatus(HelperTransactionCode.ASSIGNED_STATUS_ACK_IN);
//                }
//
//            }
//
//            HelperBridge.sActiveOrdersList.add(activeList);
//            HelperBridge.sPlanOutstandingOrdersList.add(planOutlist);
//        }
    }

}
