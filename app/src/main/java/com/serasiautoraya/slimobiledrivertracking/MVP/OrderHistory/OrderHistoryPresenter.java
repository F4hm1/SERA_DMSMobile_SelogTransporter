package com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.error.VolleyError;
import com.google.gson.Gson;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
import com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail.OrderHistoryDetailActivity;
import com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail.OrderHistoryDetailResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.OrderHistory.OrderHistoryDetail.OrderHistoryDetailSendModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.RestClient.RestConnection;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.HttpsTrustManager;

import net.grandcentrix.thirtyinch.TiPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 22/05/2017.
 */

public class OrderHistoryPresenter extends TiPresenter<OrderHistoryView> {

    private SimpleAdapterModel mSimpleAdapterModel;
    private RestConnection mRestConnection;

    @Override
    protected void onAttachView(@NonNull final OrderHistoryView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        Log.d("DASHBOARDSS", "ORHISTOPpp: " + HelperBridge.sTempFragmentTarget);
//        HelperBridge.sTempFragmentTarget = 0;
        Log.d("DASHBOARDSS", "ORHISTOP: " + HelperBridge.sTempFragmentTarget);
        getView().initialize();
    }

    public OrderHistoryPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }

    public void onItemClicked(int position) {
        OrderHistoryResponseModel assignedOrderResponseModel = (OrderHistoryResponseModel) mSimpleAdapterModel.getItem(position);
        final String orderCode = assignedOrderResponseModel.getOrderCode();
//        setdummydata(orderCode);
//        getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, "ORDER_DUMMY", OrderHistoryDetailActivity.class);

        /*
        * TODO uncomment this to connect API
        * */

        HelperBridge.sOrderHistoryDetailActivityList = new ArrayList<>();

        OrderHistoryDetailSendModel orderHistoryDetailSendModel = new OrderHistoryDetailSendModel(orderCode);
        getView().toggleLoading(true);
        final OrderHistoryView orderHistoryView = getView();
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_HISTORY_DETAIL, orderHistoryDetailSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {

                List<OrderHistoryDetailResponseModel> orderHistoryDetailResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    orderHistoryDetailResponseModels.add(Model.getModelInstance(response.getData()[i], OrderHistoryDetailResponseModel.class));
                }

                if(orderHistoryDetailResponseModels.size() > 0){
                    HelperBridge.sOrderHistoryDetailActivityList = orderHistoryDetailResponseModels;
                    getView().changeActivityAction(HelperKey.KEY_INTENT_ORDERCODE, orderCode, OrderHistoryDetailActivity.class);
                }
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                /*
                * TODO change this!
                */
                orderHistoryView.showToast("FAILLLLSSS: " + response);
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                /*
                * TODO change this!
                */
                orderHistoryView.showToast("FAIL: " + error.toString());
                orderHistoryView.toggleLoading(false);
            }
        });
    }

    public void initialOrderHistoryDatePeriod() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(com.serasiautoraya.slimobiledrivertracking.helper.HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        Calendar calendarMulai = Calendar.getInstance(TimeZone.getDefault());
        calendarMulai.set(Calendar.DAY_OF_MONTH, 1);
        String startdate = dateFormatter.format(calendarMulai.getTime());

        Calendar calendarAkhir = Calendar.getInstance(TimeZone.getDefault());
        calendarAkhir.set(Calendar.DAY_OF_MONTH, 1);
        calendarAkhir.set(Calendar.MONTH, calendarAkhir.get(Calendar.MONTH) + 1);
        String enddate = dateFormatter.format(calendarAkhir.getTime());

        getView().setTextStartDate(startdate);
        getView().setTextEndDate(enddate);
    }

    public void loadOrderHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        HelperBridge.sOderHistoryList = new ArrayList<>();
        if (!HelperBridge.sOderHistoryList.isEmpty()) {
            getView().toggleEmptyInfo(false);
        } else {
            getView().toggleEmptyInfo(true);
        }
        mSimpleAdapterModel.setItemList(HelperBridge.sOderHistoryList);
        getView().refreshRecyclerView();

        getView().toggleLoading(true);
        final OrderHistoryView orderHistoryView = getView();
        OrderHistorySendModel assignedOrderSendModel = new OrderHistorySendModel(HelperBridge.sModelLoginResponse.getPersonalId(), startDate, endDate);
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_ORDER_HISTORY, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<OrderHistoryResponseModel> assignedOrderResponseModels = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    assignedOrderResponseModels.add(Model.getModelInstance(response.getData()[i], OrderHistoryResponseModel.class));
                }

                if(!assignedOrderResponseModels.isEmpty()){
                    orderHistoryView.toggleEmptyInfo(false);
                }else {
                    orderHistoryView.toggleEmptyInfo(true);
                }
                HelperBridge.sOderHistoryList = assignedOrderResponseModels;
                mSimpleAdapterModel.setItemList(HelperBridge.sOderHistoryList);
                orderHistoryView.refreshRecyclerView();
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnFail(String response) {
                orderHistoryView.toggleEmptyInfo(true);
                orderHistoryView.showToast("FAILLLLSSS: " + response);
                orderHistoryView.toggleLoading(false);
            }

            @Override
            public void callBackOnError(VolleyError error) {
                orderHistoryView.toggleEmptyInfo(true);
                orderHistoryView.showToast("FAIL: " + error.toString());
                orderHistoryView.toggleLoading(false);
            }
        });
    }
}
