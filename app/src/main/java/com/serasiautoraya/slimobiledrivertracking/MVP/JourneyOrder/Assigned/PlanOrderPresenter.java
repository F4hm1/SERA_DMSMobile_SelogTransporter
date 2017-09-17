package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallbackInterfaceJSON;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.TimeRestCallBackInterface;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.TimeRESTResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperTransactionCode;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.AcknowledgeOrderSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class PlanOrderPresenter extends TiPresenter<PlanOrderView> {

    private SimpleAdapterModel mSimpleAdapterModel;
    private RestConnection mRestConnection;

    public PlanOrderPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

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
        getView().toggleEmptyInfo(true);
        if (!HelperBridge.sPlanOutstandingOrdersList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sPlanOutstandingOrdersList);

        for (AssignedOrderResponseModel assignedOrderResponseModel:HelperBridge.sPlanOutstandingOrdersList) {
            if(assignedOrderResponseModel.getStatus().equalsIgnoreCase(HelperTransactionCode.ASSIGNED_ORDER_INACK_CODE)){
                getView().showAcknowledgeDialog(
                        assignedOrderResponseModel.getOrderID(),
                        assignedOrderResponseModel.getAssignmentId(),
                        assignedOrderResponseModel.getDestination(),
                        assignedOrderResponseModel.getOrigin(),
                        assignedOrderResponseModel.getETD(),
                        assignedOrderResponseModel.getETA(),
                        assignedOrderResponseModel.getCustomer()
                );
            }
            continue;
        }

        getView().refreshRecyclerView();
    }

    public void onItemClicked(int position){

        /*
        * TODO change the way to access id/code order list, code is the title of the list? Pass it to detail driver activity and retrieve data from API based on that.
        * Selection if status is waiting ACK or Waiting to Start
        * Flow : onCreate activity get bundle data/ordercode -> save to local field -> onAttachView call initialize -> initialize call getdata in presenter ->
        * presenter call & get data from API & show progress dialog -> when done, call getview.setDataValue
        * */

        AssignedOrderResponseModel assignedOrderResponseModel = (AssignedOrderResponseModel) mSimpleAdapterModel.getItem(position);
        String statusOrder = assignedOrderResponseModel.getStatus();

        if(statusOrder.equalsIgnoreCase(HelperTransactionCode.ASSIGNED_ORDER_INACK_CODE)){
            getView().showAcknowledgeDialog(
                    assignedOrderResponseModel.getOrderID(),
                    assignedOrderResponseModel.getAssignmentId(),
                    assignedOrderResponseModel.getDestination(),
                    assignedOrderResponseModel.getOrigin(),
                    assignedOrderResponseModel.getETD(),
                    assignedOrderResponseModel.getETA(),
                    assignedOrderResponseModel.getCustomer()
            );
        }else {
            Integer orderCode = assignedOrderResponseModel.getAssignmentId();
            loadDetailOrder(orderCode);
        }
    }

    public void onAcknowledgeOrder(final String orderCode, final Integer assignmentId){
        /*
        * TODO Post ACK order and refresh updated assigned order
        * */
        final String fOrderCode = orderCode;
        getView().toggleLoading(true);
        mRestConnection.getServerTime(new TimeRestCallBackInterface() {
            @Override
            public void callBackOnSuccess(TimeRESTResponseModel timeRESTResponseModel, String latitude, String longitude, String address) {
                String timeZoneId = RestConnection.getTimeZoneID(timeRESTResponseModel);
                String[] timeSplit = timeRESTResponseModel.getTime().split(" ");
                String date = timeSplit[0];
                String time = timeSplit[1];

                getView().toggleLoading(false);

                AcknowledgeOrderSendModel acknowledgeOrderSendModel = new AcknowledgeOrderSendModel(
                        orderCode,
                        assignmentId);
                submitAcknowledgeOrder(acknowledgeOrderSendModel);
            }

            @Override
            public void callBackOnFail(String message) {
                getView().toggleLoading(false);
                getView().showStandardDialog(message, "Perhatian");
            }
        });

    }

    private void submitAcknowledgeOrder(AcknowledgeOrderSendModel acknowledgeOrderSendModel){
        getView().toggleLoading(true);
        final PlanOrderView planOrderView = getView();
        mRestConnection.putData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.PUT_ACKNOWLEDGE_ORDER, acknowledgeOrderSendModel.getHashMapType(), new RestCallbackInterfaceJSON() {
            @Override
            public void callBackOnSuccess(JSONObject response) {
                try {
                    planOrderView.toggleLoading(false);
                    planOrderView.showToast(response.getString("responseText"));
                    planOrderView.refreshAllData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callBackOnFail(String response) {
                planOrderView.toggleLoading(false);
                planOrderView.showStandardDialog(response, "Perhatian");
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this, jadikan value nya dari string values!
                * */
                planOrderView.toggleLoading(false);
                planOrderView.showStandardDialog("Gagal melakukan ack order, silahkan periksa koneksi anda kemudian coba kembali", "Perhatian");
            }
        });
    }

    private void loadDetailOrder(Integer orderCode){
        /*
        * TODO uncomment this to connect API
        * */
        ActivityDetailSendModel activityDetailSendModel =  new ActivityDetailSendModel(orderCode);
        getView().toggleLoading(true);
        final PlanOrderView planOrderView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                HelperBridge.sActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
                getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getOrderCode(), ActivityDetailActivity.class);
                planOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                planOrderView.showToast(response);
                planOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                planOrderView.showToast("ERROR: " + error.toString());
                planOrderView.toggleLoading(false);
            }
        });
    }
}
