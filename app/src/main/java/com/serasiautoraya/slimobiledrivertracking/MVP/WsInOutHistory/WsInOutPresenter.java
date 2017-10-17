package com.serasiautoraya.slimobiledrivertracking.MVP.WsInOutHistory;

import android.support.annotation.NonNull;

import com.android.volley.error.VolleyError;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter.SimpleAdapterModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseInterface.RestCallBackInterfaceModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.BaseResponseModel;
import com.serasiautoraya.slimobiledrivertracking.MVP.BaseModel.Model;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.MVP.Helper.HelperUrl;
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
 * Created by randi on 24/07/2017.
 */

public class WsInOutPresenter extends TiPresenter<WsInOutView> {

    private RestConnection mRestConnection;
    private SimpleAdapterModel mSimpleAdapterModel;
    public List<WsInOutResponseModel> wsInOutResponseModels;

    public WsInOutPresenter(RestConnection mRestConnection) {
        this.mRestConnection = mRestConnection;
    }

    @Override
    protected void onAttachView(@NonNull final WsInOutView view) {
        super.onAttachView(view);
        HttpsTrustManager.allowAllSSL();
        getView().initialize();
    }

    public void setAdapter(SimpleAdapterModel simpleAdapterModel) {
        this.mSimpleAdapterModel = simpleAdapterModel;
    }


    public void initialWsInOutHistoryDatePeriod() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(com.serasiautoraya.slimobiledrivertracking.helper.HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        Calendar calendarMulai = Calendar.getInstance(TimeZone.getDefault());
        calendarMulai.set(Calendar.DAY_OF_MONTH, 1);
        String startdate = dateFormatter.format(calendarMulai.getTime());

        Calendar calendarAkhir = Calendar.getInstance(TimeZone.getDefault());
        String enddate = dateFormatter.format(calendarAkhir.getTime());

        getView().setTextStartDate(startdate);
        getView().setTextEndDate(enddate);
    }

    public void onItemClicked(int position) {

        WsInOutResponseModel itemSelected = (WsInOutResponseModel) mSimpleAdapterModel.getItem(position);
        getView().showDetailDialog(
                "Rekap Tanggal "+itemSelected.getDate(),
                itemSelected.getScheduleIn().equalsIgnoreCase("")?"-":itemSelected.getScheduleIn(),
                itemSelected.getScheduleOut().equalsIgnoreCase("")?"-":itemSelected.getScheduleOut(),
                itemSelected.getClockIn().equalsIgnoreCase("")?"-":itemSelected.getClockIn(),
                itemSelected.getClockOut().equalsIgnoreCase("")?"-":itemSelected.getClockOut(),
                itemSelected.getAbsence().equalsIgnoreCase("")?"-":itemSelected.getAbsence()
        );

    }

    public void loadWsInOutHistoryData(String startDate, String endDate) {
        startDate = HelperUtil.getServerFormDate(startDate);
        endDate = HelperUtil.getServerFormDate(endDate);

        wsInOutResponseModels = new ArrayList<>();
        if (!wsInOutResponseModels.isEmpty()) {
            getView().toggleEmptyInfo(false);
        } else {
            getView().toggleEmptyInfo(true);
        }
        mSimpleAdapterModel.setItemList(wsInOutResponseModels);
        getView().refreshRecyclerView();

        getView().toggleLoading(true);

//        setDummyData();
//
//        if (!wsInOutResponseModels.isEmpty()) {
//            getView().toggleEmptyInfo(false);
//        } else {
//            getView().toggleEmptyInfo(true);
//        }

        final WsInOutView orderHistoryView = getView();
        WsInOutSendModel assignedOrderSendModel = new WsInOutSendModel(HelperBridge.sModelLoginResponse.getPersonalId(), startDate, endDate);
        mRestConnection.getData(HelperBridge.sModelLoginResponse.getTransactionToken(), HelperUrl.GET_WSINOUT_HISTORY, assignedOrderSendModel.getHashMapType(), new RestCallBackInterfaceModel() {
            @Override
            public void callBackOnSuccess(BaseResponseModel response) {
                List<WsInOutResponseModel> wsInOutResponseModelsTemp = new ArrayList<>();
                for (int i = 0; i < response.getData().length; i++) {
                    wsInOutResponseModelsTemp.add(Model.getModelInstance(response.getData()[i], WsInOutResponseModel.class));
                }

                if(!wsInOutResponseModelsTemp.isEmpty()){
                    orderHistoryView.toggleEmptyInfo(false);
                }else {
                    orderHistoryView.toggleEmptyInfo(true);
                }
                wsInOutResponseModels = wsInOutResponseModelsTemp;
                mSimpleAdapterModel.setItemList(wsInOutResponseModels);
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

    private void setDummyData() {
        List<WsInOutResponseModel> wsInOutResponseModelsTemp = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            if (i % 3 == 0) {
                wsInOutResponseModelsTemp.add(new WsInOutResponseModel("22" + "-07-2017", "08:" + "00", "17:" + "00", "07:" + "56", "18:" + "23", "", "", "", "", ""));
//            } else if (i % 2 == 0) {
                wsInOutResponseModelsTemp.add(new WsInOutResponseModel("19" + "-07-2017", "08:" + "00", "17:" + "00", "07:" + "38", "", "", "", "", "", ""));
//            } else {
                wsInOutResponseModelsTemp.add(new WsInOutResponseModel("09" + "-07-2017", "08:" + "00", "17:" + "00", "", "", "09-07-2017", "", "", "", ""));
//            }
//        }
        wsInOutResponseModels = wsInOutResponseModelsTemp;
        mSimpleAdapterModel.setItemList(wsInOutResponseModels);
        getView().refreshRecyclerView();
        getView().toggleLoading(false);

    }
}
