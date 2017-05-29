package com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Assigned;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.JourneyOrder.Activity.ActivityDetailSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

/**
 * Created by Randi Dwi Nandra on 31/03/2017.
 */

public class ActiveOrderPresenter extends TiPresenter<ActiveOrderView> {

    private SimpleAdapterModel mSimpleAdapterModel;
    private RestConnection mRestConnection;

    public ActiveOrderPresenter(RestConnection mRestConnection){
        this.mRestConnection = mRestConnection;
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel){
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    @Override
    protected void onAttachView(@NonNull final ActiveOrderView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void loadOrdersdata(){
        if (!HelperBridge.sActiveOrdersList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        } else {
            getView().toggleEmptyInfo(true);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sActiveOrdersList);
        getView().refreshRecyclerView();
    }

    public void onItemClicked(int position){

        /*
        * TODO change the way to access id/code order list, code is the title of the list? Pass it to detail driver activity and retrieve data from API based on that.
        * Flow : onCreate activity get bundle data/ordercode -> save to local field -> onAttachView call initialize -> initialize call getdata in presenter ->
        * presenter call & get data from API & show progress dialog -> when done, call getview.setDataValue (not practice)
        *
        * TODO change the way to 2nd alternative, call get activity detail here -> Parse the model value to static class -> if success, change to
         * detail activity view -> initialize all view/field wtith the model from static class before
        * */

        AssignedOrderResponseModel assignedOrderResponseModel = (AssignedOrderResponseModel) mSimpleAdapterModel.getItem(position);
        String orderCode = assignedOrderResponseModel.getOrderCode();
//        setdummydata(orderCode);

        /*
        * TODO uncomment this to connect API
        * */
        ActivityDetailSendModel activityDetailSendModel =  new ActivityDetailSendModel(orderCode, HelperBridge.sModelLoginResponse.getPersonalId());
        getView().toggleLoading(true);
        final ActiveOrderView activeOrderView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_ACTIVITY, activityDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                HelperBridge.sActivityDetailResponseModel = Model.getModelInstance(response.getData()[0], ActivityDetailResponseModel.class);
                getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getOrderCode(), ActivityDetailActivity.class);
                activeOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                * */
                activeOrderView.showToast("FAILLLLSSS: " + response);
                activeOrderView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                * */
                activeOrderView.showToast("FAIL: " + error.toString());
                activeOrderView.toggleLoading(false);
            }
        });

    }

/*    private void setdummydata(String ordercode) {
        HelperBridge.sActivityDetailResponseModel = new ActivityDetailResponseModel(
          ordercode, "acticode:" + ordercode, "Loading Muatan", "#1976D2", "Loading", "1", "0", "0", "1", "242526",
                "14.55 WIB (Senin, 15 Mei 2017)", "17.55 WIB (Senin, 15 Mei 2017)", "-", "-6.3595777,107.2890902", "Pool HMS Karawang",
                "Pool HMS Rungkut", "Pool HMS Karawang", "14.55 WIB (Senin, 15 Mei 2017)", "-", "PIC HM Sampoerna", "Raw Material",
                "HINO 665SX", "B 1916 TOW", "ePOD", "Pool HMS Karawang", "14.55 WIB (Senin, 15 Mei 2017)"
        );
        getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, HelperBridge.sActivityDetailResponseModel.getOrderCode(), ActivityDetailActivity.class);
    }*/
}